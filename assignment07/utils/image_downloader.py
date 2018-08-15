import requests
from assignment07.config import config


API = "https://api.cognitive.microsoft.com/bing/v7.0/images/search"
AZURE_KEY = config['AZURE_KEY']


def fetch_image_list(restaurant, count):
    params = {
        "q": restaurant,
        "count": count
    }
    headers = {
        "Ocp-Apim-Subscription-Key": AZURE_KEY
    }

    r = requests.get(API, params=params, headers = headers)
    return r.json()['value']

