package com.mohamedbamoh.foodie.restaurant.domain.app.service.port.output.repository;

import com.mohamedbamoh.foodie.restaurant.domain.core.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
