package com.mohamedbamoh.foodie.order.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.common.domain.valueobject.RestaurantId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {

    private final List<Product> products;
    private final boolean active;

    @Builder
    private Restaurant(RestaurantId restaurantId, List<Product> products, boolean active) {
        super.setId(restaurantId);
        this.products = products;
        this.active = active;
    }
}
