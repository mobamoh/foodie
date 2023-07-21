package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.common.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.common.domain.valueobject.OrderStatus;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.OrderRepository;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import com.mohamedbamoh.foodie.order.domain.core.exception.OrderNotFoundException;
import com.mohamedbamoh.foodie.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class OrderSagaHelper {

    private final OrderRepository orderRepository;

    Order findOrder(String orderId) {
        var optionalOrder = orderRepository.findById(new OrderId(UUID.fromString(orderId)));
        if (optionalOrder.isEmpty()) {
            log.error("Order: {} cannot be found!", orderId);
            throw new OrderNotFoundException(String.format("Order: %s cannot be found!", orderId));
        }
        return optionalOrder.get();
    }

    void saveOrder(Order order) {
        orderRepository.save(order);
    }

    SagaStatus orderStatusToSagaStatus(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case PAID -> SagaStatus.PROCESSING;
            case APPROVED -> SagaStatus.SUCCEEDED;
            case CANCELLING -> SagaStatus.COMPENSATING;
            case CANCELLED -> SagaStatus.COMPENSATED;
            default -> SagaStatus.STARTED;
        };
    }
}
