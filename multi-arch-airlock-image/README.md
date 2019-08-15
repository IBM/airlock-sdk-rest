# Welcome to your new Go repository

You'll need to configure a few things to get started.

This guide assumes a Windows environment, but it should mostly apply to Mac and Linux machines as well.

## Setup
### Required information
* The host and optionally the port for your container registry (example: `myregistry.azurecr.io`). `localhost` is also allowed if you are setup with a local registry.
* Azure subscription name or ID (if using Azure Container Registry).
* Azure resource group name (if using Azure Container Registry).

### Required tools
* Docker CLI: https://docs.docker.com/install/
* * Run the Docker Quickstart Terminal after installation.
* Azure CLI (if using Azure Container Registry): https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest
* Git Bash (if your system does not have a native Bash shell).

### Required configuration
* If you are on SITECONNECTRD, Switch to the wi-fi network "ProjectNetwork".
* Verify that you have write access to the target container registry.
* Edit your application's Docker configuration in `docker-config.sh`. This includes settings such as:
* * The name of your application.
* * Version number.
* * Target platforms (OS and architecture).
* * The Docker container registry into which your images will be pushed.
* Run `docker-generate.sh`. This will perform some validation of your configuration. Follow the directions to correct your setup if needed.
* Edit `Dockerfile.template` to customize the Dockerfile for your application. This template is currently used on all platforms. The default template creates a minimal image that contains a single Go executable with no dependencies.

## Usage
Run `docker-generate.sh` to generate Dockerfiles. These will be placed in the `.generated` directory (ignored by Git). Note that any existing generated Dockerfiles will be overwritten. For this reason, you should edit `Dockerfile.template` whenever any changes need to be made.

Run `docker-build.sh` to locally build container images for all target platforms. The built images names will be of the format `<appName>-<operatingSystem>-<architecture>`. Additionally, they will be suffixed with both your application version (e.g. `:1.0`) and the standard Docker `:latest` suffixed. Finally, additional tags will be created prefixed with your container registry name. For example, `myregistry.azurecr.io/myapp-linux-amd64:1.0` (and `:latest`).

Run `docker-push.sh` to push all built container images to the registry. This also pushes multi-platform manifest lists for your application version and the standard Docker "latest" tag. Users running `docker pull` can acquire the image appropriate to their platform just by specifying your application name (e.g. `docker run myapp:latest`).

Alternatively, run `docker-release.sh` to execute `docker-generate.sh`, `docker-build.sh`, and `docker-push.sh` in sequence.

Each step includes error checking to help prevent bad configurations or bad pushes.

## Useful links
* Docker CLI reference: https://docs.docker.com/engine/reference/commandline/cli/

