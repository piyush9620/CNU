import os

config = {
    "DATABASE_HOSTNAME": os.environ['DB_HOST'],
    "DATABASE_USER": os.environ['DB_USER'],
    "DATABASE_PASSWORD": os.environ['DB_PASS'],
    "DATABASE_NAME": os.environ['DB_NAME']
}