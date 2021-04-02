#!/bin/bash
docker build -t techbox/openjdk11 .
./gradlew -p ./product-service clean build -x sonarqube
#./gradlew -p ./shopping-cart clean build -x test
#./gradlew -p ./batch-service clean build -x test
docker-compose -f ./docker-compose.yml up --build

