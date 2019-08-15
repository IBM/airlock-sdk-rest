#!/bin/bash

# The directory that the current script file lives in.
# This directory is assumed to also be the project root directory.
DOCKER_SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

"$DOCKER_SCRIPT_DIR/docker-generate.sh"
"$DOCKER_SCRIPT_DIR/docker-build.sh"
"$DOCKER_SCRIPT_DIR/docker-push.sh"
