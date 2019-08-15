#!/bin/bash
set -x #echo on
swagger-codegen generate -i http://localhost:8080/airlock/api/swagger.json -l java \
                                                          -o ../airlock-rest-client


