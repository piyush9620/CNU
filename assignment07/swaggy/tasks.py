import string

from celery import shared_task
from random import randint, random
from django.utils.crypto import get_random_string

from swaggy.models import Review


@shared_task
def generate_random_reviews(restaurant_id, count):
    for i in range(count):
        stars = randint(0, 5)
        text = get_random_string(50, string.ascii_letters)
        review = Review(stars=stars, text=text, restaurant_id=restaurant_id)
        review.save()
