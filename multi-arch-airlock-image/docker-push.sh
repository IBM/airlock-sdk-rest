#!/bin/bash

# The directory that the current script file lives in.
# This directory is assumed to also be the project root directory.
DOCKER_SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

. "$DOCKER_SCRIPT_DIR/docker-config-load.sh"

function verifyImagesBuilt() {
    echo
    echo "Verifying that all images have been built..."

    local i=0
    local missingCount=0
    local totalCount=0
    local errorCount=0
    while [ $i -lt ${#target_imageNamesInRegistry[@]} ]; do
        local imageNameInRegistry=${target_imageNamesInRegistry[$i]}

        local versionName="$imageNameInRegistry:$appVersion"
        local latestName="$imageNameInRegistry:latest"

        local imageIdVersion=$(docker image ls -q "$versionName")
        local imageIdLatest=$(docker image ls -q "$latestName")

        if [ ! "$imageIdVersion" ]; then
            echo " - [missing] $versionName"
            missingCount=$[$missingCount+1]
        else
            echo " - [  found] $versionName ($imageIdVersion)"
        fi

        if [ ! "$imageIdLatest" ]; then
            echo " - [missing] $latestName"
            missingCount=$[$missingCount+1]
        else
            echo " - [  found] $latestName ($imageIdLatest)"
        fi

        if [ ! "$imageIdVersion" = "$imageIdLatest" ]; then
            echo " - [FAILURE] $imageNameInRegistry - Image IDs for current version ($imageIdVersion) and latest version ($imageIdLatest) differ."
            errorCount=$[$errorCount+1]
        fi

        i=$[$i+1]
        totalCount=$[$totalCount+2]
    done

    if [ $missingCount -ne 0 ]; then
        echo
        echo "[ERROR] Found $missingCount missing image(s). Please run docker-build.sh to generate the required images."
        errorCount=$[$errorCount+1]
    fi

    if [ $errorCount -ne 0 ]; then
        echo
        echo "One or more errors occurred. See the output above for details."
        exit 1
    fi

    echo
    echo "Found $totalCount images to push."
}

function pushAllImages() {
    echo "Pushing images to registry '$containerRegistryName'..."

    local i=0
    local imageCount=0
    while [ $i -lt ${#target_imageNamesInRegistry[@]} ]; do
        local imageNameInRegistry=${target_imageNamesInRegistry[$i]}

        imageToPush="$imageNameInRegistry:$appVersion"
        pushImage

        imageToPush="$imageNameInRegistry:latest"
        pushImage

        i=$[$i+1]
        imageCount=$[$imageCount+2]
    done

    echo
    echo "Pushed $imageCount image(s) to registry '$containerRegistryName'."
}

function pushImage() {
    echo
    echo "Pushing image '$imageToPush'..."
    docker push "$imageToPush"
    if [ $? -ne 0 ]; then
        echo "[ERROR] Failed to push '$imageToPush'."
        echo
        echo "If this looks like a connection error, make sure that you are connected to ProjectNetwork. Pushing may not work behind the corporate proxy."
        exit 1
    fi
    echo "Pushed '$imageToPush'."
}

function pushAllManifestLists() {
    manifestListTagToPush="$appVersion"
    pushManifestList

    manifestListTagToPush="latest"
    pushManifestList
}

function pushManifestList() {
    local manifestListToPush="$target_manifestListName:$manifestListTagToPush"

    local manifestListImageNames=()
    local i=0
    while [ $i -lt ${#target_imageNames[@]} ]; do
        local imageNameInRegistry=${target_imageNamesInRegistry[$i]}
        manifestListImageNames+=("$imageNameInRegistry:$manifestListTagToPush")
        i=$[$i+1]
    done

    # Create (or recreate) the manifest list using the images that were just pushed.
    echo
    echo "Creating or revising manifest list '$manifestListToPush' to include the pushed images..."
    docker manifest create "$manifestListToPush" "${manifestListImageNames[@]}" --amend
    if [ $? -ne 0 ]; then
        echo "[ERROR] Failed to create or revise manifest list 'manifestListToPush'."
        echo
        echo "Make sure that you have enabled experimental features in '$HOME/.docker/config.json'. See this URL for details:"
        echo "https://github.com/docker/docker-ce/blob/master/components/cli/experimental/README.md"
        exit 1
    fi
    echo "Created or revised manifest list '$manifestListToPush'."

    # By default, Docker tags the images in the manifest with the OS/arch of the build machine.
    # We need to update the manifest with the actual OS/arch values for those images.
    # Docker explicitly specifies that the manifest list uses the same names as Go's GOOS and GOARCH settings.
    local i=0
    while [ $i -lt ${#manifestListImageNames[@]} ]; do
        local imageNameInRegistry=${manifestListImageNames[$i]}
        local operatingSystem=${target_operatingSystems[$i]}
        local architecture=${target_architectures[$i]}

        if [ ${target_architectures[$i]} == "arm64v8" ]; then
            local architecture="arm64"
        else
            echo "do nothing"
        fi


        echo "Configuring image '$imageNameInRegistry' in the manifest... (OS=$operatingSystem, architecture=$architecture)"
        docker manifest annotate "$manifestListToPush" "$imageNameInRegistry" --os "$operatingSystem" --arch "$architecture"
        if [ $? -ne 0 ]; then
            echo "[ERROR] Failed to configure '$imageNameInRegistry'."
            exit 1
        fi

        i=$[$i+1]
    done

    # The "--purge" flag forces it to overwrite the existing manifest for the same name/tag.
    echo
    echo "Pushing manifest list '$manifestListToPush'..."
    docker manifest push --purge "$manifestListToPush"
    if [ $? -ne 0 ]; then
        echo "[ERROR] Failed to push manifest list '$manifestListToPush'."
    fi

    echo
    echo "Finished pushing manifest list. To inspect the manifest list manually, run the following command:"
    echo "    docker manifest inspect $manifestListToPush"
}

function main() {
    verifyImagesBuilt
    pushAllImages
    pushAllManifestLists
}

main
