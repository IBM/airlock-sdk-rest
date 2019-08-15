#!/bin/bash

# Name of this application.
# Allowed characters: a-z (lowercase), 0-9
# Allowed symbols: '-', '.', '_' (must not appear at start or end)
# This will be used in the names of executables and Docker images.
appName="airlock-rest-api"

# Application version. Must consist of one or more positive integers
# separated by '.'. Optionally may be followed with an alphanumeric
# string after a '-'. Docker images will be tagged with this version,
# as well as "latest". It is recommended to update this value as
# needed to maintain semantic versioning. Note that lowering this
# version will still cause that version to be pushed as "latest".
# Example: "1.7.87-beta2".
appVersion="1.0"

# List of (OS, architecture) pairs describing the target platforms for this application.
#
# See this link for a list of potential values:
#     ($GOOS = operating system, $GOARCH = architecture)
#     https://golang.org/doc/install/source#environment
targetPlatforms=(
    #"linux" "i386"
    "linux" "amd64"
    "linux" "arm64"
)

# Path to the file to use as a template for all generated Dockerfiles.
# The path should be relative to the directory that contains this config file.
dockerfileTemplate="Dockerfile.template"

# Endpoint for the container registry that container images should be pushed to.
# This can contain the hostname and optionally the port in the format "hostname:port".
# Pther prefixes and suffixes (such as "http://") should be omitted.
containerRegistryName="ncpdev.azurecr.io"
