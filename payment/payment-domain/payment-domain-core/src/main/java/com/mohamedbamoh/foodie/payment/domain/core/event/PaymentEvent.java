package com.mohamedbamoh.foodie.payment.domain.core.event;

import com.mohamedbamoh.foodie.common.domain.event.DomainEvent;
import com.mohamedbamoh.foodie.payment.domain.core.entity.Payment;
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
