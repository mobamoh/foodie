package com.mohamedbamoh.foodie.order.domain.core.event;

import com.mohamedbamoh.foodie.common.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent {

//    private final DomainEventPublisher<OrderCancelledEvent> orderCancalledEventDomainEventPublisher;

    public OrderCancelledEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
//        this.orderCancalledEventDomainEventPublisher = orderCancalledEventDomainEventPublisher;
    }

//    @Override
//    public void fire() {
//        orderCancalledEventDomainEventPublisher.publish(this);
//    }

}
