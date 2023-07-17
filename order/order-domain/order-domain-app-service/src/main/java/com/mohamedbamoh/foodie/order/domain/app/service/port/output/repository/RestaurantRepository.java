package com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository;

import com.mohamedbamoh.foodie.order.domain.core.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
