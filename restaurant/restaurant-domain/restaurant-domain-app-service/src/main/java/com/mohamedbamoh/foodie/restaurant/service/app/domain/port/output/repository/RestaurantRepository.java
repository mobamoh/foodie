package com.mohamedbamoh.foodie.restaurant.service.app.domain.port.output.repository;

import com.mohamedbamoh.foodie.restaurant.core.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
