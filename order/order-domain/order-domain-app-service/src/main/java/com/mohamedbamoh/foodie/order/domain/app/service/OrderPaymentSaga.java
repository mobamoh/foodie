package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.common.domain.event.EmptyEvent;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.PaymentResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.restaurant.OrderPaidRestaurantRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.domain.core.OrderDomainService;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderPaidEvent;
import com.mohamedbamoh.foodie.saga.SagaStep;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class OrderPaymentSaga implements SagaStep<PaymentResponse, OrderPaidEvent, EmptyEvent> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher;

    @Override
    @Transactional
    public OrderPaidEvent process(PaymentResponse paymentResponse) {
        log.info("Completing payment for order: {}", paymentResponse.getOrderId());
        var order = orderSagaHelper.findOrder(paymentResponse.getOrderId());
        var orderPaidEvent = orderDomainService.payOrder(order, orderPaidRestaurantRequestMessagePublisher);
        orderSagaHelper.saveOrder(order);
        log.info("Order: {} is paid", order.getId().getValue());
        return orderPaidEvent;
    }

    @Override
    @Transactional
    public EmptyEvent rollback(PaymentResponse paymentResponse) {
        log.info("Cancelling payment for order: {}", paymentResponse.getOrderId());
        var order = orderSagaHelper.findOrder(paymentResponse.getOrderId());
        orderDomainService.cancelOrder(order, paymentResponse.getFailureMessages());
        orderSagaHelper.saveOrder(order);
        log.info("Order: {} is cancelled", order.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

}
