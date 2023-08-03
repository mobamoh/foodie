package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.CreateOrderResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.mapper.OrderDataMapper;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.scheduler.payment.PaymentOutboxHelper;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class OrderCreateCommandHandler {

    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final PaymentOutboxHelper paymentOutboxHelper;
    private final OrderSagaHelper orderSagaHelper;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        var orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        //orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        var orderResponse = orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),
                "Order created successfully");

        paymentOutboxHelper.save(orderDataMapper.orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent),
                orderCreatedEvent.getOrder().getOrderStatus(),
                orderSagaHelper.orderStatusToSagaStatus(orderCreatedEvent.getOrder().getOrderStatus()),
                OutboxStatus.STARTED,
                UUID.randomUUID());
        log.info("Returning CreateOrderResponse with order: {}",orderCreatedEvent.getOrder().getId());
        return orderResponse;
    }


}
