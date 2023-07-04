package com.mohamedbamoh.foodie.order.service.domain.port.output.message.publisher.payment;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderCancalledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancalledEvent> {
}
