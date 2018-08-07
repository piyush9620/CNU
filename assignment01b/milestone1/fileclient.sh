#!/bin/sh

docker volume create clientvol;

docker build client/ -t "cnu-ass01b-client";

# Running
docker run --rm --mount source=clientvol,target=/clientdata --net cnu-ass01b-network --name cnu-ass01b-client-container --env PORT=$1 --env SERVER_HOSTNAME=cnu-ass01b-server-container.cnu-ass01b-network cnu-ass01b-client
