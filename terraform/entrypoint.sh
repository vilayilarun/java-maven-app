#!/bin/bash
sudo dnf update -y && sudo dnf install docker -y
sudo usermode -aG docker $(whoami)
# Install docker compose 
sudo curl -SL https://github.com/docker/compose/releases/download/v2.6.1/docker-compose-linux-x86_64 -o /usr/bin/docker-compose
sudo chmod +x /usr/bin/docker-compose