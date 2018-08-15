#!/bin/bash

# Start Gunicorn processes
aws s3 cp s3://cnu-2k18/arpit/a07c/config.py assignment07c/config.py

python3 manage.py collectstatic

python3 manage.py migrate

nginx

echo Starting Gunicorn.
exec gunicorn assignment07c.wsgi:application \
    --bind 0.0.0.0:8000 \
    --workers 3
