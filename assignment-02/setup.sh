#!/bin/sh

docker volume create servervol;
docker volume create clientvol;
docker network create cnu-ass02-network

docker build client/ -t "cnu-ass02-client";
docker build server/ -t "cnu-ass02-server";

# Running
# docker run --mount source=servervol,target=/serverdata --net cnu-ass02-network --name cnu-ass02-server-container cnu-ass02-server /bin/sh /server.sh
# docker run --mount source=clientvol,target=/clientdata --net cnu-ass02-network --name cnu-ass02-client-container cnu-ass02-client /bin/sh /client.sh
