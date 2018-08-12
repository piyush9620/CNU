import boto3
import botocore
from image_downloader import ImageDownloader

s3 = boto3.resource('s3')

BUCKET_NAME = "cnu-2k18"
KEY = "images.txt"
THREADS = 5

def image_generator(images):
    for image in images:
        yield image


def main():
    try:
        s3.Bucket(BUCKET_NAME).download_file(KEY, 'images.txt')
    except botocore.exceptions.ClientError as e:
        if e.response['Error']['Code'] == "404":
            print("The object does not exist.")
        else:
            raise

    images = open('images.txt').readlines()
    images = image_generator(images)
    threads = []

    for i in range(THREADS):
        threads.append(ImageDownloader(i+1, images))
        threads[i].start()

    for t in threads:
        t.join()


if __name__ == '__main__':
    main()