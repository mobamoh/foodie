package com.mohamedbamoh.foodie.order.domain.core.event;

import com.mohamedbamoh.foodie.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {
//    private final DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher;

    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
//        this.orderPaidEventDomainEventPublisher = orderPaidEventDomainEventPublisher;
    }

//    @Override
//    public void fire() {
//        orderPaidEventDomainEventPublisher.publish(this);
//    }

}
