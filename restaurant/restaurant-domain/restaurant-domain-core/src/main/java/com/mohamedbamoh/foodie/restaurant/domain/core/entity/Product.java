package com.mohamedbamoh.foodie.restaurant.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.common.domain.valueobject.Money;
import com.mohamedbamoh.foodie.common.domain.valueobject.ProductId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product extends BaseEntity<ProductId> {

    private String name;
    private Money price;
    private final Integer quantity;
    private Boolean available;

    @Builder
    public Product(ProductId productId, String name, Money price, Integer quantity, Boolean available) {
        super.setId(productId);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    public void updateWithConfirmedNamePriceAndAvailability(String name, Money price, Boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }
}
