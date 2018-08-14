from django.contrib import messages
from django.http import HttpResponse, HttpResponseBadRequest
from django.shortcuts import redirect
from django.urls import reverse

from swaggy.models import Restaurant
from swaggy.tasks import generate_random_reviews


def index(request):
    restaurant_id = request.GET.get("restaurant_id")
    count = request.GET.get("count")
    if not restaurant_id or not count:
        return HttpResponseBadRequest("Give me something!")

    if not Restaurant.objects.get(pk=restaurant_id):
        return HttpResponseBadRequest("You don't know nothing!")

    generate_random_reviews.delay(int(restaurant_id), int(count))
    messages.success(request, 'Doing bro!')
    return redirect(reverse("admin:swaggy_review_changelist"))
