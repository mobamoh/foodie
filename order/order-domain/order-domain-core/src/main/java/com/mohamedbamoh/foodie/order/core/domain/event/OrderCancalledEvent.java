package com.mohamedbamoh.foodie.order.core.domain.event;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.core.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancalledEvent extends OrderEvent {

    private final DomainEventPublisher<OrderCancalledEvent> orderCancalledEventDomainEventPublisher;

    public OrderCancalledEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderCancalledEvent> orderCancalledEventDomainEventPublisher) {
        super(order, createdAt);
        this.orderCancalledEventDomainEventPublisher = orderCancalledEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderCancalledEventDomainEventPublisher.publish(this);
    }
}
