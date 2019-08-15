#!/bin/bash

# The directory that the current script file lives in.
# This directory is assumed to also be the project root directory.
DOCKER_SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

. "$DOCKER_SCRIPT_DIR/docker-config-load.sh"

function generateDockerfiles() {
    echo
    echo "Generating Dockerfiles..."

    local i=0
    local generatedCount=0
    while [ $i -lt ${#target_dockerfileNames[@]} ]; do
        local dockerfileName=${target_dockerfileNames[$i]}
        local operatingSystem=${target_operatingSystems[$i]}
        local architecture=${target_architectures[$i]}

        # Windows executables have an extension of ".exe". All others have no extension.
        local extension=""
        if [ "$operatingSystem" = "windows" ]; then
            extension=".exe"
        fi

        cp --force "$DOCKER_SCRIPT_DIR/$dockerfileTemplate" "$dockerfileName"
        if [ $? -ne 0 ]; then
            echo " - [ERROR] Failed to copy '$DOCKER_SCRIPT_DIR/$dockerfileTemplate' to '$dockerfileName'."
            exit 1
        fi

        # Remove comments starting with "##".
        sed -i "/^##.*$/d" "$dockerfileName"
        if [ $? -ne 0 ]; then
            echo " - [ERROR] Failed to generate '$dockerfileName' (step: 1)."
            exit 1
        fi

        # Generate the Dockerfile by replacing variables in the template.
        sed -e "s|\${OperatingSystem}|$operatingSystem|g" \
            -e "s|\${Architecture}|$architecture|g" \
            -e "s|\${AppName}|$appName|g" \
            -e "s|\${ExecutableExtension}|$extension|g" \
            -i "$dockerfileName"
        if [ $? -ne 0 ]; then
            echo " - [ERROR] Failed to generate '$dockerfileName' (step: 2)."
            exit 1
        fi

        generatedCount=$[$generatedCount+1]
        echo " - $dockerfileName"

        i=$[$i+1]
    done

    echo
    echo "Generated $generatedCount Dockerfile(s)."
}

function main() {
    generateDockerfiles
}

main
