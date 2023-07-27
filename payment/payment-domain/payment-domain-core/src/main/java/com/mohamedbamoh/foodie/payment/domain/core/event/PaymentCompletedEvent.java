package com.mohamedbamoh.foodie.payment.domain.core.event;

import com.mohamedbamoh.foodie.payment.domain.core.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent {

//    private final DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher;
    public PaymentCompletedEvent(Payment payment, ZonedDateTime createdAt) {
        super(payment, createdAt, Collections.emptyList());
//        this.paymentCompletedEventDomainEventPublisher = paymentCompletedEventDomainEventPublisher;
    }

//    @Override
//    public void fire() {
//        paymentCompletedEventDomainEventPublisher.publish(this);
//    }
}
