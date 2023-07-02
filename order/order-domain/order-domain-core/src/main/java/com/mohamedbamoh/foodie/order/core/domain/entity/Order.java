package com.mohamedbamoh.foodie.order.core.domain.entity;

import com.mohamedbamoh.foodie.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.domain.valueobject.*;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.StreetAddress;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.TrackingId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
}
