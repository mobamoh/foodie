package com.mohamedbamoh.foodie.order.core.domain.event;

import com.mohamedbamoh.foodie.order.core.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancalledEvent extends OrderEvent {
    public OrderCancalledEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
