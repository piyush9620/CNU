from django.contrib import admin
from django.dispatch import receiver

from swaggy.models import Restaurant, Review
from swaggy.models import Category
from django.db.models import Avg, signals


class CategoryAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'business_count')
    readonly_fields = ('business_count', )


class RestaurantAdmin(admin.ModelAdmin):
    list_display = (
        "name", "neighbourhood", "address", "city", "state", "postal_code", "latitude", "longitude", "stars",
        "categories_name", "review_count", "is_open", "removed"
    )

    list_filter = (
        "city", "is_open", "categories__name"
    )

    readonly_fields = ("removed", "review_count", "stars")

    def categories_name(self, obj):
        return ", ".join([a.name for a in obj.categories.all()])

    categories_name.admin_order_field = "categories"
    categories_name.short_description = 'Categories'


class ReviewAdmin(admin.ModelAdmin):
    list_display = ("id", "restaurant_id", "stars", "date", "text")
    list_filter = ("restaurant__id",)

    def restaurant_id(self, obj):
        return obj.restaurant.name

    def has_delete_permission(self, request, obj=None):
        return False

    def has_change_permission(self, request, obj=None):
        return False


# Register your models here.
admin.site.register(Category, CategoryAdmin)
admin.site.register(Restaurant, RestaurantAdmin)
admin.site.register(Review, ReviewAdmin)
