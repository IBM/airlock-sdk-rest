#!/bin/bash

./gradlew test --tests com.ibm.app.services.*  -Dairlock.test.external.url=http://localhost:8080/airlock/api