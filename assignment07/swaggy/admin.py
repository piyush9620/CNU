from django.contrib import admin
from swaggy.models import Restaurant, Review
from swaggy.models import Category
from django.db.models import Avg


class CategoryAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'businesses_count')

    def businesses_count(self, obj):
        # count field2
        return obj.restaurants.count()


class RestaurantAdmin(admin.ModelAdmin):
    list_display = (
        "name", "neighbourhood", "address", "city", "state", "postal_code", "latitude", "longitude", "stars",
        "categories_name", "review_count", "is_open", "removed"
    )

    def stars(self, obj):
        return list(obj.reviews.aggregate(Avg('stars')).values())[0]

    def review_count(self, obj):
        return obj.reviews.count()

    def categories_name(self, obj):
        return ", ".join([a.name for a in obj.categories.all()])

    categories_name.admin_order_field = "categories"
    categories_name.short_description = 'Categories'


# Register your models here.
admin.site.register(Category, CategoryAdmin)
admin.site.register(Restaurant, RestaurantAdmin)
admin.site.register(Review)
