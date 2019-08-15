#!/bin/bash
set -x #echo on

cd  /home/nvidia/airlock/

echo "load images scale docker image"
docker build -t airlock-rest-api .


echo "remove scale-rest-api container"
docker rm -f airlock-rest-api || true

echo "run container"
docker run -d -p 8080:8080 --name airlock-rest-api --memory-swap 150m --memory 150m --rm \
-v /cache:/airlock-server/cache \
-v /SSD:/airlock-server/SSD \
-e CACHE_VOLUME=cache \
-e SSD_CACHE_VOLUME=SSD \
airlock-rest-api


docker ps

#./run-gnome-test.sh