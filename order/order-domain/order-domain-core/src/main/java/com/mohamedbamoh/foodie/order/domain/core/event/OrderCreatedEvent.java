package com.mohamedbamoh.foodie.order.domain.core.event;

import com.mohamedbamoh.foodie.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {

//    private final DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher;

    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
//        this.orderCreatedEventDomainEventPublisher = orderCreatedEventDomainEventPublisher;
    }

//    @Override
//    public void fire() {
//        orderCreatedEventDomainEventPublisher.publish(this);
//    }

}
