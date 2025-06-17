package com.restaurant.Service;

import com.restaurant.Entity.Restaurant;
import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Long id, Restaurant restaurant);
    void deleteRestaurant(Long id);
    List<Restaurant> getAllRestaurants();
    Restaurant findRestaurantById(Long id);
}