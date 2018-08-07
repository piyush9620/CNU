#!/bin/sh

docker volume create servervol;
docker volume create clientvol;

docker build client/ -t "cnu-ass02-client";
docker build server/ -t "cnu-ass02-server";

# Running
# docker run --mount source=servervol,target=/serverdata cnu-ass02-server -p 9999:9999 /bin/sh /server.sh
# docker run --mount source=clientvol,target=/clientdata cnu-ass02-client -p 9999:9999 /bin/sh /client.sh
