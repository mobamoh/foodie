package com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository;

import com.mohamedbamoh.foodie.common.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import com.mohamedbamoh.foodie.order.domain.core.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(OrderId orderId);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
