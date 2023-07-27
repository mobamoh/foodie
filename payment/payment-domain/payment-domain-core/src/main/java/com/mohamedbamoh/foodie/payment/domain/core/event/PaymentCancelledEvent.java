package com.mohamedbamoh.foodie.payment.domain.core.event;

import com.mohamedbamoh.foodie.payment.domain.core.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCancelledEvent extends PaymentEvent {

//    private final DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher;

    public PaymentCancelledEvent(Payment payment, ZonedDateTime createdAt) {
        super(payment, createdAt, Collections.emptyList());
//        this.paymentCancelledEventDomainEventPublisher = paymentCancelledEventDomainEventPublisher;
    }

//    @Override
//    public void fire() {
//        paymentCancelledEventDomainEventPublisher.publish(this);
//    }
}
