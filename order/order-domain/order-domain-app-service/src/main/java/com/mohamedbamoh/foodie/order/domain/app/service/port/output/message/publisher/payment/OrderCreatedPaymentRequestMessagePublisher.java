package com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.payment;

import com.mohamedbamoh.foodie.common.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
