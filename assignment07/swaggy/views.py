from celery import group
from django.contrib import messages
from django.http import HttpResponse, HttpResponseBadRequest
from django.shortcuts import redirect
from django.urls import reverse

from swaggy.models import Restaurant
from swaggy.tasks import generate_random_review


def index(request):
    restaurant_id = request.GET.get("restaurant_id")
    count = int(request.GET.get("count", 1))
    if not restaurant_id or not count:
        return HttpResponseBadRequest("Give me something!")

    restaurant_id = int(restaurant_id)

    if not Restaurant.objects.get(pk=restaurant_id):
        return HttpResponseBadRequest("You don't know nothing!")

    group(generate_random_review.s(restaurant_id) for i in range(count))()

    messages.success(request, "Wait for it!")
    return redirect(reverse("admin:swaggy_review_changelist"))
