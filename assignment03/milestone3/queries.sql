-- Average number of reviews posted each day
select count(*)/count(DISTINCT created_at) as average_reviews_per_day from reviews;

-- Highest rated restaurants with at least 30 reviews
select * from restaurants where review_count > 30 order by rating desc limit 30;

-- User which has maximum upvotes on his reviews.


-- Average number of reviews per user
select count(*)/count(distinct reviewer_id) as average_reviews_per_user from reviews;

-- Total revenue from all restaurants.
SELECT SUM(items.price) as total_revenue FROM orders, items, order_item_maps WHERE orders.id = order_item_maps.order_id AND items.id = order_item_maps.item_id

-- User who has purchased the most.
SELECT users.id, users.username, users.fullname, SUM(items.price) FROM users, orders, items, order_item_maps
WHERE orders.id = order_item_maps.order_id AND items.id = order_item_maps.item_id AND orders.owner_id = users.id
ORDER BY SUM(items.price)

-- Delete a user.
DELETE FROM users WHERE username="username"

-- Submit an order.
