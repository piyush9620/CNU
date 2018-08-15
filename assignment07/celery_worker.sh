#!/bin/sh

celery -A assignment07 worker -l info &

celery -A assignment07 flower --address=0.0.0.0 --port=5555 --basic_auth=$CELERY_USERNAME:$CELERY_PASSWORD;
