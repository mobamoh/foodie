package com.mohamedbamoh.foodie.order.service.data.access.restaurant.mapper;

import com.mohamedbamoh.foodie.common.data.access.restaurant.entity.RestaurantEntity;
import com.mohamedbamoh.foodie.common.data.access.restaurant.exception.RestaurantDataAccessException;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.domain.valueobject.ProductId;
import com.mohamedbamoh.foodie.domain.valueobject.RestaurantId;
import com.mohamedbamoh.foodie.order.core.domain.entity.Product;
import com.mohamedbamoh.foodie.order.core.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToProducts(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        var restaurantEntity = restaurantEntities.stream().findFirst()
                .orElseThrow(() -> new RestaurantDataAccessException("Restaurant not found!"));

        var products = restaurantEntities.stream().map(entity ->
                new Product(new ProductId(entity.getProductId()),
                        entity.getProductName(), new Money(entity.getPrice()))
        ).toList();

        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getId()))
                .products(products)
                .active(restaurantEntity.getActive())
                .build();
    }
}
