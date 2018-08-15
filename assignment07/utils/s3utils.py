import boto3
import botocore
import requests
from assignment07.config import config

s3 = boto3.resource('s3')

BUCKET_NAME = config['S3_BUCKET']
OUTPUT_PATH = config['S3_KEY']


def download_image_to_s3(image_url, image_id):
    key = "{}/{}".format(OUTPUT_PATH, image_id)
    data = requests.get(image_url).content

    s3.Bucket(BUCKET_NAME).put_object(Key=key, Body=data)
    return "s3://{}/{}".format(BUCKET_NAME, key)
