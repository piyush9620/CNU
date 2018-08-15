import os

config = {
    "DATABASE_HOSTNAME": os.environ.get('DB_HOST', "localhost"),
    "DATABASE_USER": os.environ.get('DB_USER', ""),
    "DATABASE_PASSWORD": os.environ.get('DB_PASS', ""),
    "DATABASE_NAME": os.environ.get('DB_NAME', "assignment07"),
    "CELERY_BROKER": os.environ.get('CELERY_BROKER', "redis://localhost:6379/0"),
    "CELERY_BACKEND": os.environ.get('CELERY_BACKEND', "redis://localhost:6379/0"),
    "AZURE_KEY": os.environ.get('AZURE_KEY', ""),
    "S3_BUCKET": os.environ.get('S3_BUCKET', "cnu-2k18"),
    "S3_KEY": os.environ.get('S3_KEY', "arpit/a07b")
}