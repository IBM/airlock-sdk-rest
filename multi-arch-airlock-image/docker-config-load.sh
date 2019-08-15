# The directory that the current script file lives in.
# This directory is assumed to also be the project root directory.
DOCKER_SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

. "$DOCKER_SCRIPT_DIR/docker-config.sh"

target_manifestListName="$containerRegistryName/$appName" # Multi-architecture manifest list name (without ":version" or ":latest").

# Parallel arrays containing data about Docker images (one entry per OS/arch pair).
target_operatingSystems=()     # Operating systems.
target_architectures=()        # Architectures.
target_dockerfileNames=()      # Generated Dockerfiles.
target_imageNames=()           # Image names (without ":version" or ":latest").
target_imageNamesInRegistry=() # Image names when pushing to $containerRegistryName (without ":version" or ":latest").



function validateConfig() {
    echo
    echo "Validating docker-config.sh..."

    validateAppName
    validateAppVersion
    validateTargetPlatforms
    validateDockerfileTemplate
    validateContainerRegistryName
    #validateDockerConfigurationFile
}

function validateAppName() {
    # Validate that the application name is specified and matches the correct format.
    local VALID_IMAGE_NAME_REGEX="^[a-z0-9][a-z0-9_.-]*[a-z0-9]$"
    if [[ ! "$appName" =~ $VALID_IMAGE_NAME_REGEX ]]; then
        echo " - [ERROR] Application name '$appName' is invalid. Names must only contain a-z (lowercase) and 0-9. Names may also contain '-', '_', and '.', but not at the beginning or end."
        exit 1
    fi

    # Validate the length of the application name.
    # Final Docker image names can contain $appName, the container registry name ("namegoeshere.azurecr.io/"),
    # a port (":8880"), OS and architecture names ("-windows-amd64"), and a tag (e.g. "latest"). Since the
    # maximum image name length is 128 characters, we limit the $appName to 64 characters.
    local appNameLength=${#appName}
    if [ $appNameLength -gt 64 ]; then
        echo " - [ERROR] Application name '$appName' ($appNameLength characters) is too long. Names must be 64 characters or less."
    fi
    echo " - Application name '$appName' ($appNameLength characters) is valid."
}

function validateAppVersion() {
    # Verify that the version is specified and matches the correct format.
    local VALID_VERSION_REGEX="^[0-9](\\.[0-9]+)*(-[a-z0-9]+)?$"
    if [[ ! "$appVersion" =~ $VALID_VERSION_REGEX ]]; then
        echo " - [ERROR] Application version '$appVersion' is invalid. Versions must consist of positive integers separated by '.', optionally followed by a '-' and an alphanumeric string."
        exit 1
    fi
    echo " - Application version '$appVersion' is valid."
}

function validateTargetPlatforms() {
    # Verify that the list of target platforms is not empty.
    local targetPlatformsArrayCount=${#targetPlatforms[@]}
    if [ $targetPlatformsArrayCount -eq 0 ]; then
        echo " - [ERROR] Please specify at least one target platform."
        exit 1
    fi

    # Verify that the number of values in $targetPlatforms is even (i.e. consists of OS/arch pairs).
    # The list of acceptable values may change, so we do not validate them. Bad values will fail at the build step.
    let remainder=$targetPlatformsArrayCount%2
    if [ $remainder -eq 1 ]; then
        echo " - [ERROR] Found an uneven number of values for targetPlatforms. Please make sure that your platforms are listed in pairs like this: \"<OS>\" \"<arch>\""
        exit 1
    fi
    echo " - Target platforms are valid."
}

function validateDockerfileTemplate() {
    # Verify that the template file exists.
    if [ ! -f "$DOCKER_SCRIPT_DIR/$dockerfileTemplate" ]; then
        echo " - [ERROR] Dockerfile template '$DOCKER_SCRIPT_DIR/$dockerfileTemplate' was not found."
        exit 1
    else
        echo " - Dockerfile template '$DOCKER_SCRIPT_DIR/$dockerfileTemplate' was found."
    fi
}

function validateContainerRegistryName() {
    local VALID_CONTAINER_REGISTRY_REGEX="^[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)*(:[0-9]+)?$"
    if [[ ! "$containerRegistryName" =~ $VALID_CONTAINER_REGISTRY_REGEX ]]; then
        echo " - [ERROR] Container registry name '$containerRegistryName' is invalid. Must consist of a hostname and optionally a port number. Other prefixes and suffixes (such as 'https://' should be omitted."
        exit 1
    fi
    echo " - Container registry name '$containerRegistryName' is valid."
}

function validateDockerConfigurationFile() {
    # Verify that the file exists. Create it if necessary (with experimental features enabled so that Docker manifests are supported).
    local dockerConfigFile="$HOME/.docker/config.json"
    if [ ! -f "$dockerConfigFile" ]; then
        echo '{ "experimental": "enabled" }' > "$dockerConfigFile"
        if [ $? -ne 0 ]; then
            echo " - [ERROR] Docker configuration file '$dockerConfigFile' was not found and could not be created automatically. Please create this file and enable experimental features according to this page: https://github.com/docker/docker-ce/blob/master/components/cli/experimental/README.md"
            exit 1
        fi
        echo " - [WARNING] Docker configuration file '$dockerConfigFile' was not found. It has been created automatically. Experimental features have been enabled."
    fi
    echo " - Found Docker configuration file '$dockerConfigFile'."

    # Verify that the "experimental" flag is enabled in config.json.
    cat "$dockerConfigFile" | tr -s '\n' ' ' | tr -s '\r\n' ' ' | tr -s '\r' ' ' | tr -s '[:space:]' ' ' | grep -Pq '"experimental"\s*:\s*"enabled"'
    if [ $? -ne 0 ]; then
        echo " - [ERROR] Flag for experimental features was not found in Docker configuration file '$dockerConfigFile'. Please edit the file to enable experimental features according to this page: https://github.com/docker/docker-ce/blob/master/components/cli/experimental/README.md"
        exit 1
    fi
    echo " - Experimental features are enabled in Docker configuration."

    # Verify that the user has logged in to their container registry.
    cat "$dockerConfigFile" | tr -s '\n' ' ' | tr -s '\r\n' ' ' | tr -s '\r' ' ' | tr -s '[:space:]' ' ' | grep -Pq "\"auths\":\s*\{\s*\"$containerRegistryName\"\s*:"
    if [ $? -ne 0 ]; then
        echo " - [ERROR] Authorization token not found in Docker configuration file '$dockerConfigFile' for container registry '$containerRegistryName'. Please log in by running:"
        echo "       docker login $containerRegistryName"
        echo "   If using Azure Container Registry, log in by running the following in this shell or in a PowerShell window:"
        echo "       az acr login --name <YOUR_USERNAME> --subscription <SUBSCRIPTION_NAME_OR_ID> --resource-group <RESOURCE_GROUP_NAME>"
        exit 1
    fi
    echo " - Found authorization token for container registry '$containerRegistryName' in Docker configuration."
}



function loadConfig() {
    # Make a directory for generated files if it doesn't exist.
    local dockerfileDirectory="$DOCKER_SCRIPT_DIR/.generated"
    mkdir -p "$dockerfileDirectory"

    local i=0
    while [ $i -lt ${#targetPlatforms[@]} ]; do
        local operatingSystem=${targetPlatforms[$i]}
        local architecture=${targetPlatforms[$i+1]}
        local dockerfileName="$dockerfileDirectory/Dockerfile-$operatingSystem-$architecture"
        local imageName="$appName-$operatingSystem-$architecture"
        local imageNameInRegistry="$containerRegistryName/$imageName"

        target_operatingSystems+=("$operatingSystem")
        target_architectures+=("$architecture")
        target_dockerfileNames+=("$dockerfileName")
        target_imageNames+=("$imageName")
        target_imageNamesInRegistry+=("$imageNameInRegistry")

        i=$[$i+2]
    done
}

function main() {
    validateConfig
    loadConfig
}

main
