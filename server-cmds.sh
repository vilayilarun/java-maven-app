#!/usr/bin/env bash
export IMG=35.200.245.75:8083/java-maven:$1
docker-compose -f docker-compose.yaml up -d