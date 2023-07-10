package com.mohamedbamoh.foodie.payment.core.domain.valueobject;

import com.mohamedbamoh.foodie.domain.valueobject.BaseId;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
