package com.mohamedbamoh.foodie.payment.core.domain.event;

import com.mohamedbamoh.foodie.domain.event.DomainEvent;
import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public abstract class PaymentEvent implements DomainEvent<Payment> {

    private final Payment payment;
    private final ZonedDateTime createdAt;
    private final List<String> failureMessages;
}
