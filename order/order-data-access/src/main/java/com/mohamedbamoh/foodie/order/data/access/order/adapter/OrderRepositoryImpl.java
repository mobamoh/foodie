package com.mohamedbamoh.foodie.order.data.access.order.adapter;

import com.mohamedbamoh.foodie.common.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import com.mohamedbamoh.foodie.order.domain.core.valueobject.TrackingId;
import com.mohamedbamoh.foodie.order.data.access.order.repository.OrderJpaRepository;
import com.mohamedbamoh.foodie.order.data.access.order.mapper.OrderDataAccessMapper;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public Order save(Order order) {
        var orderEntity = orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order));
        return orderDataAccessMapper.orderEntityToOrder(orderEntity);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
