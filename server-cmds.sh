#!/usr/bin/env bash
export IMG=35.200.245.75:8083/java-maven:$1
export DOCKER_PWD=$2
export DOCKER_USR=$3
echo $DOCKER_PWD | docker login -u $DOCKER_USR 35.200.245.75:8083 --password-stdin
docker-compose -f docker-compose.yaml up -d