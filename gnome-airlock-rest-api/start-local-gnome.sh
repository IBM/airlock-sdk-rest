#!/bin/bash
set -x #echo on


./gradlew clean build -x test fatJar
cp ../debug-console.zip debug-console.zip

echo "build airlock docker image"
docker build -t airlock-rest-api .


echo "remove airlock-rest-api container"
docker rm -f airlock-rest-api

echo "run container"
docker run -p 8080:8080 --name airlock-rest-api --memory-swap 150m --memory 150m --rm \
-v /Users/<user>/IdeaProjects/airlock-rest-java-sdk-wrapper/cache:/airlock-server/cache \
-v /Users/<user>/IdeaProjects/airlock-rest-java-sdk-wrapper/SSD:/airlock-server/SSD \
-e CACHE_VOLUME=cache \
-e SSD_CACHE_VOLUME=SSD \
-e VERBOSE=true \
ncpdev.azurecr.io/airlock-rest-api-linux-amd64