package com.mohamedbamoh.foodie.payment.core.domain.event;

import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent {
    public PaymentCompletedEvent(Payment payment, ZonedDateTime createdAt) {
        super(payment, createdAt, Collections.emptyList());
    }
}
