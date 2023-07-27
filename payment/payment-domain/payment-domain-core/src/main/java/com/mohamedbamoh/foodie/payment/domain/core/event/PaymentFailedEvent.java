package com.mohamedbamoh.foodie.payment.domain.core.event;

import com.mohamedbamoh.foodie.payment.domain.core.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent {

//    private final DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher;

    public PaymentFailedEvent(Payment payment, ZonedDateTime createdAt, List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
//        this.paymentFailedEventDomainEventPublisher = paymentFailedEventDomainEventPublisher;
    }

//    @Override
//    public void fire() {
//        paymentFailedEventDomainEventPublisher.publish(this);
//    }
}
