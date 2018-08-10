package com.cnu.assignment04.controllers;

import com.cnu.assignment04.entities.Cuisine;
import com.cnu.assignment04.entities.Restaurant;
import com.cnu.assignment04.repositories.CuisineRepository;
import com.cnu.assignment04.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(path="/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public @ResponseBody Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        for (String cuisineName : restaurant.getCuisineNames()) {
            Cuisine cuisine = new Cuisine(cuisineName);
            restaurant.getCuisines().add(cuisine);
            cuisine.getRestaurants().add(restaurant);
        }
        restaurantRepository.save(restaurant);
        return null;
    }

    @GetMapping(path="/")
    public @ResponseBody Iterable<Restaurant> getRestaurants() {
        Pageable limit = PageRequest.of(0,10);

        // This returns a JSON or XML with the users
        return restaurantRepository.getNRestaurants(1, limit);
    }
}