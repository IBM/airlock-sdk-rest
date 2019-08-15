#!/usr/bin/env bash



cd  ../gnome-airlock-rest-api
./gradlew build fatJar -x test
cd  ../multi-arch-airlock-image

cp ../gnome-airlock-rest-api/build/libs/gnome-airlock-rest-api-1.0.0.jar src/airlock-rest-api-1.0.0.jar

docker run --rm -w /work -v ${PWD}:/work ubuntu:18.04 ./docker-generate.sh
./docker-build.sh
#docker run --privileged --rm -w /work -v ${PWD}:/work amd64/docker:stable-dind chmod 777 ./docker-build.sh && ./docker-build.sh
./docker-push.sh