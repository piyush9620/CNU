from django.db import models


class Category(models.Model):
    name = models.CharField(max_length=200)

    class Meta:
        verbose_name_plural = 'Categories'


class Restaurant(models.Model):
    name = models.CharField(max_length=200)
    neighbourhood = models.CharField(max_length=200)
    address = models.CharField(max_length=200)
    city = models.CharField(max_length=200)
    state = models.CharField(max_length=200)
    postal_code = models.CharField(max_length=200)
    latitude = models.DecimalField(max_digits=10, decimal_places=8)
    longitude = models.DecimalField(max_digits=11, decimal_places=8)
    is_open = models.BooleanField()
    category = models.ForeignKey(Category, related_name="restaurants", on_delete=models.CASCADE)


class Review(models.Model):
    stars = models.FloatField()
    restaurant = models.ForeignKey(Restaurant, related_name="reviews", on_delete=models.CASCADE)
