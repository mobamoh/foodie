package com.mohamedbamoh.foodie.payment.core.domain.event;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent {

    private final DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher;
    public PaymentCompletedEvent(Payment payment, ZonedDateTime createdAt, DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher) {
        super(payment, createdAt, Collections.emptyList());
        this.paymentCompletedEventDomainEventPublisher = paymentCompletedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        paymentCompletedEventDomainEventPublisher.publish(this);
    }
}
