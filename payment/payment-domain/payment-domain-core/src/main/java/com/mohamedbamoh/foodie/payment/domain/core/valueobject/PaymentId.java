package com.mohamedbamoh.foodie.payment.domain.core.valueobject;

import com.mohamedbamoh.foodie.common.domain.valueobject.BaseId;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
