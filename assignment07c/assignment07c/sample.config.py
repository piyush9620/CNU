import os


config = {
    "DATABASE_HOSTNAME": os.environ.get('DB_HOST', "localhost"),
    "DATABASE_USER": os.environ.get('DB_USER', "root"),
    "DATABASE_PASSWORD": os.environ.get('DB_PASS', ""),
    "DATABASE_NAME": os.environ.get('DB_NAME', "assignment07c")
}