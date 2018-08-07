#!/bin/sh

docker volume create servervol;
docker network create cnu-ass01b-network;

docker build server/ -t "cnu-ass01b-server";

# Running
docker run --rm -d --mount source=servervol,target=/serverdata --net cnu-ass01b-network --name cnu-ass01b-server-container --env PORT=$1 cnu-ass01b-server
