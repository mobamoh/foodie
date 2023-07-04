package com.mohamedbamoh.foodie.order.service.domain.port.output.repository;

import com.mohamedbamoh.foodie.order.core.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
