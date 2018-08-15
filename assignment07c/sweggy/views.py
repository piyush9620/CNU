import django_filters
from sweggy.serializers import RestaurantSerializer, ItemSerializer, CuisineSerializer
from rest_framework import viewsets
from sweggy.models import Restaurant, Item, Cuisine


class ItemFilter(django_filters.FilterSet):
    name = django_filters.CharFilter(lookup_expr='icontains', field_name='name')
    maxPrice = django_filters.NumberFilter(lookup_expr='lte', field_name='price')
    minPrice = django_filters.NumberFilter(lookup_expr='gte', field_name='price')

    class Meta:
        model = Item
        fields = {}


class RestaurantFilter(django_filters.FilterSet):
    name = django_filters.CharFilter(lookup_expr='icontains', field_name='name')
    cuisine = django_filters.CharFilter(lookup_expr='icontains', field_name='cuisines__name')
    city = django_filters.CharFilter(lookup_expr='icontains', field_name='city')

    class Meta:
        model = Restaurant
        fields = {}


class RestaurantViewSet(viewsets.ModelViewSet):
    queryset = Restaurant.objects.all()
    serializer_class = RestaurantSerializer
    filter_class = RestaurantFilter


class ItemViewSet(viewsets.ModelViewSet):
    queryset = Item.objects.all()
    serializer_class = ItemSerializer
    filter_class = ItemFilter


class CuisineViewSet(viewsets.ModelViewSet):
    queryset = Cuisine.objects.all()
    serializer_class = CuisineSerializer
