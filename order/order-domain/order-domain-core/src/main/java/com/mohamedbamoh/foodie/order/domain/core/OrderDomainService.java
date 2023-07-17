package com.mohamedbamoh.foodie.order.domain.core;

import com.mohamedbamoh.foodie.common.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import com.mohamedbamoh.foodie.order.domain.core.entity.Restaurant;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCancelledEvent;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCreatedEvent;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher);

    OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancalledEventDomainEventPublisher);

    void cancelOrder(Order order, List<String> failureMessages);
}
