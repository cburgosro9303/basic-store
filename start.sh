#!/bin/bash
./product-service/gradlew -p ./product-service clean build
docker-compose -f ./docker-compose.yml up --build

