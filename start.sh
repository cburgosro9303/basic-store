#!/bin/bash
docker build -t experis/test .
./gradlew -p ./product-service clean build
./gradlew -p ./shopping-cart clean build -x test
./gradlew -p ./batch-service clean build -x test
docker-compose -f ./docker-compose.yml up --build

