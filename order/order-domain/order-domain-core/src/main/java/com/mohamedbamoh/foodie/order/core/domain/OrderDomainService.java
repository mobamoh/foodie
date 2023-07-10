package com.mohamedbamoh.foodie.order.core.domain;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.core.domain.entity.Order;
import com.mohamedbamoh.foodie.order.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderCancalledEvent;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderCreatedEvent;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher);

    OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher);

    void approveOrder(Order order);

    OrderCancalledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancalledEvent> orderCancalledEventDomainEventPublisher);

    void cancelOrder(Order order, List<String> failureMessages);
}
