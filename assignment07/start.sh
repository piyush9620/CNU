#!/bin/bash

# Start Gunicorn processes
aws s3 cp s3://cnu-2k18/arpit/assignment07/config.py /assignment07/assignment07/config.py

python3 manage.py migrate

nginx

echo Starting Gunicorn.
exec gunicorn assignment07.wsgi:application \
    --bind 0.0.0.0:8000 \
    --workers 3


