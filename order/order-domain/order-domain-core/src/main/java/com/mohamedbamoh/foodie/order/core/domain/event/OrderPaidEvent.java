package com.mohamedbamoh.foodie.order.core.domain.event;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.core.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {
    private final DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher;

    public OrderPaidEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher) {
        super(order, createdAt);
        this.orderPaidEventDomainEventPublisher = orderPaidEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderPaidEventDomainEventPublisher.publish(this);
    }
}
