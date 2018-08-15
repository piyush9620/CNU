from abc import ABC

from rest_framework.exceptions import ValidationError

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
    latitude = serializers.DecimalField(max_digits=10, decimal_places=8, min_value=-90, max_value=90)
    longitude = serializers.DecimalField(max_digits=11, decimal_places=8, min_value=-180, max_value=180)
    rating = serializers.IntegerField(min_value=0)

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
    # restaurant = RestaurantSerializer()
    restaurant_id = serializers.IntegerField()

    class Meta:
        model = Item
        exclude = ("restaurant", )

    def validate(self, attrs):
        if attrs['price'] < 0:
            raise ValidationError("Price cannot be less than 0")

        return attrs

