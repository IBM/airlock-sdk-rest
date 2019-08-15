#!/bin/bash

# The directory that the current script file lives in.
# This directory is assumed to also be the project root directory.
DOCKER_SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

. "$DOCKER_SCRIPT_DIR/docker-config-load.sh"

function regenerateDockerfilesIfNecessary() {
    echo
    echo "Checking modification time of '$dockerfileTemplate'..."

    local dockerfileTemplatePath="$DOCKER_SCRIPT_DIR/$dockerfileTemplate"
    local modificationTimeFilePath="$DOCKER_SCRIPT_DIR/.generated/.templatemodified"

    # Get the current modification timestamp (integer).
    local modificationTime=$( stat --format %Y "$dockerfileTemplatePath" )
    echo "Current modification time: $modificationTime"

    # Try to load the previous modification timestamp from the file $modificationTimeFilePath.
    if [ -f "$modificationTimeFilePath" ]; then
        local previousModificationTime=$( cat "$modificationTimeFilePath" )
        echo "Modification time at last build: $previousModificationTime"

        # Regenerate Dockerfiles even if the current modification time is older
        # than the previous build. This guarantees that the build reflects the current
        # repository state after git checkout, git reset, etc.
        if [ "$modificationTime" != "$previousModificationTime" ]; then
            echo
            echo "Dockerfile template has been modified since the last build. Regenerating Dockerfiles..."
            echo

            . "$DOCKER_SCRIPT_DIR/docker-generate.sh"
            echo
        else
            echo
            echo "Dockerfile template has not changed. Using existing generated Dockerfiles."
        fi
    else
        echo
        echo "Previous modification time could not be found. Using existing generated Dockerfiles."
    fi

    # Update $modificationTimeFilePath with the current modification timestamp.
    echo "$modificationTime" > "$modificationTimeFilePath"
}

function verifyDockerfilesExist() {
    echo
    echo "Verifying that Dockerfiles exist..."

    local i=0
    while [ $i -lt ${#target_dockerfileNames[@]} ]; do
        local dockerfileName=${target_dockerfileNames[$i]}
        if [ ! -f "$dockerfileName" ]; then
            echo " - [ERROR] Dockerfile '$dockerfileName' does not exist. Please run 'docker-generate.sh' to generate the Dockerfiles for this project."
            exit 1
        fi
        echo " - $dockerfileName"

        i=$[$i+1]
    done
}

function buildImages() {
    echo
    echo "Building Docker images for $appName..."
    echo "============================================================================="

    local i=0
    while [ $i -lt ${#target_dockerfileNames[@]} ]; do
        local dockerfileName=${target_dockerfileNames[$i]}
        local imageName=${target_imageNames[$i]}
        local imageNameInRegistry=${target_imageNamesInRegistry[$i]}

        echo
        echo "Building image '$imageName' (tags: '$appVersion', 'latest') with Dockerfile: '$dockerfileName'...";
        echo

        # Note that it is also possible to define further build-time variables for use in Dockerfile scripts.
        # See here: https://docs.docker.com/edge/engine/reference/commandline/build/#set-build-time-variables-build-arg
        docker build . \
            --file "$dockerfileName" \
            --tag "$imageName:$appVersion" \
            --tag "$imageNameInRegistry:$appVersion" \
            --tag "$imageName:latest" \
            --tag "$imageNameInRegistry:latest"

        if [ $? -ne 0 ]; then
            echo "[ERROR] Build of '$imageName' failed. If the output above says 'error during connect', please verify the status of your Docker machine by running:"
            echo "        docker-machine ls"
            echo "    If necessary, start the machine again by using:"
            echo "        docker-machine start"
            echo "    If the error says 'Unable to load the service index', then please verify your proxy settings in ~/.docker/config.json."
            exit 1
        fi
        echo
        echo "Finished building image '$imageName'."

        echo
        echo "------------------------------"
        i=$[$i+1]
    done
}

function main() {
    #regenerateDockerfilesIfNecessary
    verifyDockerfilesExist
    buildImages
}

main
