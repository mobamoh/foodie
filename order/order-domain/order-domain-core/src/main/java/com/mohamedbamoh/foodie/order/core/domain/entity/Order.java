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

@Builder
@Getter
public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

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
            throw new OrderDomainException(String.format("Order total price %f is not equal to items total price %f!",
                    this.price.getAmount(), orderItemsTotalPrice.getAmount()));
        }
    }

    private void validateItemPrice(OrderItem item) {
        if (!item.isPriceValid()) {
            throw new OrderDomainException(String.format("Order item price %f is not valid for product %s!",
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
}
