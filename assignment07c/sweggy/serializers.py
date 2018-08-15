from abc import ABC

from sweggy.models import Restaurant, Cuisine, Item
from rest_framework import serializers


class ItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = Item
        fields = "__all__"


class CuisineSerializer(serializers.ModelSerializer):
    class Meta:
        model = Cuisine
        fields = "__all__"
        read_only_fields = ('business_count',)

    def to_internal_value(self, data):
        cuisine = Cuisine.objects.get_or_create(name=data)[0]
        return self.to_representation(cuisine)


class RestaurantSerializer(serializers.ModelSerializer):
    cuisines = CuisineSerializer(many=True)

    class Meta:
        model = Restaurant
        fields = "__all__"

    def create(self, validated_data):
        cuisines = validated_data.pop("cuisines")
        cuisine_ids = [item['id'] for item in cuisines]
        cuisines = Cuisine.objects.filter(pk__in=cuisine_ids)
        restaurant = Restaurant(**validated_data)
        restaurant.save()
        restaurant.cuisines.add(*cuisines)
        return restaurant
