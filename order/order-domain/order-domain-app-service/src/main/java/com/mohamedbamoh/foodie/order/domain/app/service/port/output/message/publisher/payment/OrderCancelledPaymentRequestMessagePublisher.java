package com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.payment;

import com.mohamedbamoh.foodie.common.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
