import json
from swaggy_handler import createAddress, createOrGetLocation, createRestaurant, createItemTypes


def main():
    businesses = open('dataset/business.json')

    business_mapping = {}

    for business in businesses.readlines():
        if not business:
            continue

        business = json.loads(business)
        locality = business['neighborhood']
        district = business['city']
        state = business['state']
        country = "USA"
        pincode = business['postal_code']
        location_id = createOrGetLocation(locality, district, state, country, pincode)

        address = business['address']
        latitude = business['latitude']
        longitude = business['longitude']
        address_id = createAddress(address, location_id, latitude, longitude)

        name = business['name']
        rating = business['stars']
        review_count = business['review_count']
        restaurant_id = createRestaurant(name, address_id, rating, review_count)

        item_types = business['categories']
        createItemTypes(item_types)

        business_mapping[business['business_id']] = restaurant_id

    with open('business_mapping.json', 'w') as f:
        json.dump(business_mapping, f)


if __name__ == '__main__':
    main()