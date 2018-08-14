#!/bin/bash

# Start Gunicorn processes
python3 manage.py migrate

nginx

echo Starting Gunicorn.
exec gunicorn assignment07.wsgi:application \
    --bind 0.0.0.0:8000 \
    --workers 3


