#!/bin/sh

if [ "$CELERY_WORKER" ]; then
    /bin/sh celery_worker.sh;
fi;

if [ "$SERVER" ]; then
    /bin/sh server.sh;
fi;