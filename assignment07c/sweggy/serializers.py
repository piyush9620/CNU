from abc import ABC

from sweggy.models import Restaurant, Cuisine, Item
from rest_framework import serializers


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

    def update(self, instance, validated_data):
        instance.name = validated_data['name']
        instance.city = validated_data['city']
        instance.latitude = validated_data['latitude']
        instance.longitude = validated_data['longitude']
        instance.rating = validated_data['rating']
        instance.is_open = validated_data['is_open']
        instance.save()
        cuisine_ids = [item['id'] for item in validated_data['cuisines']]
        cuisines = Cuisine.objects.filter(pk__in=cuisine_ids)
        instance.cuisines.remove(*instance.cuisines.all())
        instance.cuisines.add(*cuisines)
        return instance

    def to_representation(self, instance):
        data = super(RestaurantSerializer, self).to_representation(instance)
        data['cuisines'] = [item["name"] for item in data['cuisines']]
        return data


class ItemSerializer(serializers.ModelSerializer):
    restaurant = RestaurantSerializer()

    class Meta:
        model = Item
        fields = "__all__"
