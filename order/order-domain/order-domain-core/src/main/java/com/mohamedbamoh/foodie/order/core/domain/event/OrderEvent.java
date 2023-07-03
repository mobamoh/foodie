package com.mohamedbamoh.foodie.order.core.domain.event;

import com.mohamedbamoh.foodie.domain.event.DomainEvent;
import com.mohamedbamoh.foodie.order.core.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public abstract class OrderEvent implements DomainEvent<Order> {

    private final Order order;
    private final ZonedDateTime createdAt;
}
