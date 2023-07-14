package com.mohamedbamoh.foodie.restaurant.data.access.adapter;

import com.mohamedbamoh.foodie.common.data.access.restaurant.repository.RestaurantJpaRepository;
import com.mohamedbamoh.foodie.restaurant.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.restaurant.data.access.mapper.RestaurantDataAccessMapper;
import com.mohamedbamoh.foodie.restaurant.service.app.domain.port.output.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        var restaurantProductIds = restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);
        var optionalRestaurantEntities = restaurantJpaRepository.findByIdAndProductIdIn(restaurant.getId().getValue(), restaurantProductIds);
        return optionalRestaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
