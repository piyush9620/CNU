from django.core.exceptions import ValidationError
from django.db import models
from django.db.models import signals
from django.dispatch import receiver
from django_permanent.managers import MultiPassThroughManager
from django_permanent.models import PermanentModel
from django_permanent.query import PermanentQuerySet


class Category(PermanentModel):
    all_objects = MultiPassThroughManager(PermanentQuerySet)
    name = models.CharField(max_length=200)
    business_count = models.IntegerField(default=0)

    class Meta:
        verbose_name_plural = 'Categories'


class Restaurant(PermanentModel):
    all_objects = MultiPassThroughManager(PermanentQuerySet)
    name = models.CharField(max_length=200)
    neighbourhood = models.CharField(max_length=200)
    address = models.CharField(max_length=200)
    city = models.CharField(max_length=200)
    state = models.CharField(max_length=200)
    postal_code = models.CharField(max_length=200)
    latitude = models.DecimalField(max_digits=10, decimal_places=8)
    longitude = models.DecimalField(max_digits=11, decimal_places=8)
    is_open = models.BooleanField()
    categories = models.ManyToManyField(Category, related_name="restaurants")
    stars = models.FloatField(default=0)
    review_count = models.IntegerField(default=0)


class Review(PermanentModel):
    all_objects = MultiPassThroughManager(PermanentQuerySet)
    stars = models.IntegerField()
    restaurant = models.ForeignKey(Restaurant, related_name="reviews", on_delete=models.CASCADE)
    date = models.DateTimeField(auto_now_add=True)
    text = models.TextField()


@receiver(signals.pre_save, sender=Review)
def validate_review_stars(sender, instance, **kwargs):
    if instance.stars is not None and isinstance(instance.stars, int) \
            and 5 >= instance.stars >= 0:
        pass
    else:
        raise ValidationError("Stars must be between 0 and 5")


@receiver(signals.post_save, sender=Restaurant)
def update_categories(sender, instance, **kwargs):

    return 1