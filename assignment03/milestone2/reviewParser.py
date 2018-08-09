import json
from swaggy_handler import createReview
from datetime import datetime
import csv

def main():
    reviews = open('dataset/reviews/xmm')
    restaurant_mappings = json.load(open('business_mapping.json'))
    user_mappings = json.load(open('user_mappings.json'))

    review_data = []

    for review in reviews.readlines():
        if not review:
            continue

        review = json.loads(review)

        rating = review['stars']
        created_at = review['date']
        description = review['text']
        restaurant_id = restaurant_mappings[review['business_id']]
        reviewer_id = user_mappings[review['user_id']]
        # created_at = "STR_TO_DATE({}, '%Y-%m-%d')".format(created_at)
        review_data.append({
            "rating": rating,
            "created_at": created_at,
            "description": description,
            "restaurant_id": restaurant_id,
            "reviewer_id": reviewer_id
        })

    with open('reviews.csv', 'w') as csv_file:
        writer = csv.DictWriter(csv_file, fieldnames=['rating', 'created_at', 'description', 'restaurant_id', 'reviewer_id'])
        writer.writeheader()
        writer.writerows(review_data)


if __name__ == '__main__':
    main()