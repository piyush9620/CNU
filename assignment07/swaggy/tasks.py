import string

from celery import shared_task, chord, chain, group
from random import randint, random
from django.utils.crypto import get_random_string

from swaggy.models import Review, Restaurant, Image
from utils import image_downloader
from utils.s3utils import download_image_to_s3


@shared_task
def generate_random_review(restaurant_id):
    stars = randint(0, 5)
    text = get_random_string(50, string.ascii_letters)
    review = Review(stars=stars, text=text, restaurant_id=restaurant_id)
    review.save()


@shared_task
def fetch_image_list(restaurant, count=10):
    return image_downloader.fetch_image_list(restaurant, count)


@shared_task
def save_image(link_to_s3, image_name, restaurant_id):
    image = Image(restaurant_id=restaurant_id, link=link_to_s3, name=image_name)
    image.save()


@shared_task
def download_image(image_url, image_id):
    return download_image_to_s3(image_url, image_id)


@shared_task
def download_and_save(image, restaurant_id):
    return chain(
        download_image.s(image['contentUrl'], image['imageId']),
        save_image.s(image['name'], restaurant_id)
    )()


@shared_task
def download_all_and_save(images, restaurant_id):
    return group(download_and_save.s(image, restaurant_id) for image in images)()


@shared_task
def update_restaurant_images(restaurant_id):
    restaurant = Restaurant.objects.get(pk=restaurant_id)
    restaurant.images_added = True
    restaurant.save()


@shared_task
def download_images(restaurant_id, restaurant_name):
    COUNT = 10
    chain(
        fetch_image_list.s(restaurant_name, COUNT),
        download_all_and_save.s(restaurant_id),
        update_restaurant_images.si(restaurant_id)
    )()
