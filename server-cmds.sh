#!/usr/bin/env bash
export IMG=vilayilarun/max:java-maven-$1
export DOCKER_PWD=$2
export DOCKER_USR=$3
echo $DOCKER_PWD | docker login -u $DOCKER_USR --password-stdin
docker-compose -f docker-compose.yaml up -d
