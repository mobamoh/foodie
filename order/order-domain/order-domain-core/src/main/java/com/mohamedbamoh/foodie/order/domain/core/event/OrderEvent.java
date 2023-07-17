package com.mohamedbamoh.foodie.order.domain.core.event;

import com.mohamedbamoh.foodie.common.domain.event.DomainEvent;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public abstract class OrderEvent implements DomainEvent<Order> {

    private final Order order;
    private final ZonedDateTime createdAt;
}
