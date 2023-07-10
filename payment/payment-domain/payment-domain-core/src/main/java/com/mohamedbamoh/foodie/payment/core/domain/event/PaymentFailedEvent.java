package com.mohamedbamoh.foodie.payment.core.domain.event;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent {

    private final DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher;

    public PaymentFailedEvent(Payment payment, ZonedDateTime createdAt, List<String> failureMessages, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher) {
        super(payment, createdAt, failureMessages);
        this.paymentFailedEventDomainEventPublisher = paymentFailedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        paymentFailedEventDomainEventPublisher.publish(this);
    }
}
