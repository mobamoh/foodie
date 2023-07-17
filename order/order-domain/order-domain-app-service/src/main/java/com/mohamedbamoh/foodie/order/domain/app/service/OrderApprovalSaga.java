package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.common.domain.event.EmptyEvent;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.RestaurantApprovalResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.OrderRepository;
import com.mohamedbamoh.foodie.order.domain.core.OrderDomainService;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCancelledEvent;
import com.mohamedbamoh.foodie.saga.SagaStep;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponse, EmptyEvent, OrderCancelledEvent> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;

    @Override
    @Transactional
    public EmptyEvent process(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Approving order: {}", restaurantApprovalResponse.getOrderId());
        var order = orderSagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        orderDomainService.approveOrder(order);
        orderSagaHelper.saveOrder(order);
        log.info("Order: {} has been approved", order.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public OrderCancelledEvent rollback(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Cancelling order: {}", restaurantApprovalResponse.getOrderId());
        var order = orderSagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        var orderCancelledEvent = orderDomainService.cancelOrderPayment(order, restaurantApprovalResponse.getFailureMessages(),
                orderCancelledPaymentRequestMessagePublisher);
        orderSagaHelper.saveOrder(order);
        log.info("Order: {} has been cancelled", order.getId().getValue());
        return orderCancelledEvent;
    }
}
