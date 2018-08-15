from django.db import models


# Create your models here.
class Cuisine(models.Model):
    name = models.CharField(max_length=255, null=False)
    business_count = models.IntegerField(default=0)


class Restaurant(models.Model):
    name = models.CharField(max_length=200, null=False)
    neighbourhood = models.CharField(max_length=200, null=True, blank=True)
    address = models.CharField(max_length=200, null=True, blank=True)
    city = models.CharField(max_length=200, null=True, blank=True)
    state = models.CharField(max_length=200, null=True, blank=True)
    postal_code = models.CharField(max_length=200, null=True, blank=True)
    latitude = models.DecimalField(max_digits=10, decimal_places=8, null=True)
    longitude = models.DecimalField(max_digits=11, decimal_places=8, null=True)
    is_open = models.BooleanField(default=True)
    cuisines = models.ManyToManyField(Cuisine, related_name="restaurants")


class Item(models.Model):
    name = models.CharField(max_length=200, null=False)
    price = models.FloatField(null=False)
    restaurant = models.ForeignKey(Restaurant, related_name="items", on_delete=models.CASCADE)
