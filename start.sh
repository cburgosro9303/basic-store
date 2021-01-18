#!/bin/bash
./batch-service/gradlew -p ./batch-service clean build
./product-service/gradlew -p ./product-service clean build
docker-compose -f ./docker-compose.yml up --build

