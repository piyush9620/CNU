from django.conf.urls import url, include
from rest_framework import routers
from sweggy import views

router = routers.DefaultRouter()
router.register(r'restaurants', views.RestaurantViewSet)
router.register(r'cuisines', views.CuisineViewSet)
router.register(r'items', views.ItemViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),
]
