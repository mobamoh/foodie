package com.mohamedbamoh.foodie.order.core.domain.entity;

import com.mohamedbamoh.foodie.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.OrderItemId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final Integer quantity;
    private final Money price;
    private final Money subTotal;
}
