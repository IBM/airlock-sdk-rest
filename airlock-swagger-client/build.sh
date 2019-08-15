#!/bin/bash
set -x #echo on

docker run --rm -w /work  -v ${PWD}:/work maven mvn install