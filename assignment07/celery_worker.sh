#!/bin/sh

celery -A assignment07 worker -l info &

celery -A assignment07 flower --port=5555 --basic_auth=$CELERY_USERNAME:$CELERY_PASSWORD;
