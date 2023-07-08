package com.mohamedbamoh.foodie.order.core.domain.entity;

import com.mohamedbamoh.foodie.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.domain.valueobject.*;
import com.mohamedbamoh.foodie.order.core.domain.exception.OrderDomainException;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.OrderItemId;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.StreetAddress;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.TrackingId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

//@Builder
@Getter
public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    @Builder
    private Order(OrderId orderId, CustomerId customerId, RestaurantId restaurantId, StreetAddress deliveryAddress, Money price, List<OrderItem> items, TrackingId trackingId, OrderStatus orderStatus, List<String> failureMessages) {
        super.setId(orderId);
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddress = deliveryAddress;
        this.price = price;
        this.items = items;
        this.trackingId = trackingId;
        this.orderStatus = orderStatus;
        this.failureMessages = failureMessages;
    }

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void initializeOrder() {
        super.setId(new OrderId(UUID.randomUUID()));
        this.trackingId = new TrackingId(UUID.randomUUID());
        this.orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    private void initializeOrderItems() {
        var itemId = 1L;
        for (var item : items) {
            item.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        var orderItemsTotalPrice = this.items.stream().map(item -> {
            validateItemPrice(item);
            return item.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!this.price.equals(orderItemsTotalPrice)) {
            throw new OrderDomainException(String.format("Order total price %.2f is not equal to items total price %.2f!", this.price.getAmount(), orderItemsTotalPrice.getAmount()));
        }
    }

    private void validateItemPrice(OrderItem item) {
        if (!item.isPriceValid()) {
            throw new OrderDomainException(String.format("Order item price %.2f is not valid for product %s!",
                    item.getPrice().getAmount(), item.getProduct().getId().getValue()));
        }
    }

    private void validateTotalPrice() {
        if (this.price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException("Order total price must be greater than zero!");
        }
    }

    private void validateInitialOrder() {
        if (this.orderStatus != null || super.getId() != null) {
            throw new OrderDomainException("Order is not in correct initialization state!");
        }
    }

    public void pay() {
        if (this.orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not in correct state for payment!");
        }
        this.orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if (this.orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not in correct state for approval!");
        }
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (this.orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not in correct state for cancel initialization!");
        }
        this.orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages) {
        if (!(this.orderStatus == OrderStatus.PENDING
                || this.orderStatus == OrderStatus.CANCELLING)) {
            throw new OrderDomainException("Order is not in correct state for cancelling!");
        }
        this.orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(m -> !m.isEmpty()).toList());
        }
        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }
}
