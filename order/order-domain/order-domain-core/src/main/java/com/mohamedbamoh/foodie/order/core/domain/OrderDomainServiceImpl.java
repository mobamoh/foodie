package com.mohamedbamoh.foodie.order.core.domain;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.core.domain.entity.Order;
import com.mohamedbamoh.foodie.order.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderCancalledEvent;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderCreatedEvent;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderPaidEvent;
import com.mohamedbamoh.foodie.order.core.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.mohamedbamoh.foodie.domain.DomainConstants.UTC;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher) {
        validateRestaurant(restaurant);
        setOrderProductInfo(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderCreatedEventDomainEventPublisher);
    }

    private void setOrderProductInfo(Order order, Restaurant restaurant) {
        // TODO: Optimize complexity using HashMap
        order.getItems().forEach(item -> restaurant.getProducts().forEach(product -> {
            var currentProduct = item.getProduct();
            if (currentProduct.equals(product)) {
                currentProduct.updateWithConfirmedNameAndPrice(product.getName(), product.getPrice());
            }
        }));
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException(
                    String.format("Restaurant with id %s is currently not active!",
                            restaurant.getId().getValue()));
        }
    }

    @Override
    public OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderPaidEventDomainEventPublisher);
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved", order.getId().getValue());
    }

    @Override
    public OrderCancalledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancalledEvent> orderCancalledEventDomainEventPublisher) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id: {}", order.getId().getValue());
        return new OrderCancalledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderCancalledEventDomainEventPublisher);
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
    }
}
