package com.mohamedbamoh.foodie.payment.service.domain.port.output.message.publisher;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentCompletedEvent;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}
