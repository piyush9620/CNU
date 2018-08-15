from django.db import models


# Create your models here.
class Cuisine(models.Model):
    name = models.CharField(max_length=255)


class Restaurant(models.Model):
    name = models.CharField(max_length=200)
    city = models.CharField(max_length=200, blank=True)
    latitude = models.DecimalField(max_digits=10, decimal_places=8)
    longitude = models.DecimalField(max_digits=11, decimal_places=8)
    is_open = models.BooleanField()
    rating = models.FloatField()
    cuisines = models.ManyToManyField(Cuisine, related_name="restaurants")


class Item(models.Model):
    name = models.CharField(max_length=200)
    price = models.FloatField()
    restaurant = models.ForeignKey(Restaurant, related_name="items", on_delete=models.CASCADE)
