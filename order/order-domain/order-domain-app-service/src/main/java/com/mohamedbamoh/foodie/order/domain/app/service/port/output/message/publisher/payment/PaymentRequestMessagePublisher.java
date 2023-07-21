package com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.payment;

import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.payment.OrderPaymentOutboxMessage;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {
    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
