#!/bin/bash
docker build -t experis/test .
./batch-service/gradlew -p ./batch-service clean build -x test
./product-service/gradlew -p ./product-service clean build
docker-compose -f ./docker-compose.yml up --build

