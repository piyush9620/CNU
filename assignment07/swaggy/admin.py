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
        "name", "neighbourhood", "address", "city", "state", "postal_code", "latitude", "longitude", "stars", "category_name",
        "review_count", "is_open"
    )

    list_display_links = ('category_name', )

    def stars(self, obj):
        return list(obj.reviews.aggregate(Avg('stars')).values())[0]

    def review_count(self, obj):
        return obj.reviews.count()

    def category_name(self, obj):
        return obj.category.name

    category_name.admin_order_field = "category"
    category_name.short_description = 'Category'


# Register your models here.
admin.site.register(Category, CategoryAdmin)
admin.site.register(Restaurant, RestaurantAdmin)
admin.site.register(Review)
