package com.mohamedbamoh.foodie.order.service.data.access.restaurant.adapter;

import com.mohamedbamoh.foodie.order.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.order.service.data.access.restaurant.mapper.RestaurantDataAccessMapper;
import com.mohamedbamoh.foodie.order.service.data.access.restaurant.repository.RestaurantJpaRepository;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        var productIds = restaurantDataAccessMapper.restaurantToProducts(restaurant);
        var restaurants = restaurantJpaRepository.findByIdAndProductIdIn(restaurant.getRestaurantId().getValue(), productIds);
        return restaurants.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}