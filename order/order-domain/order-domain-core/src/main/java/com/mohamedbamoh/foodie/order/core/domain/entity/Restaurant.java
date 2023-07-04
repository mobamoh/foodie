package com.mohamedbamoh.foodie.order.core.domain.entity;

import com.mohamedbamoh.foodie.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.domain.valueobject.RestaurantId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {

    private final RestaurantId restaurantId;
    private final List<Product> products;
    private boolean active;

}
