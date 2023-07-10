package com.mohamedbamoh.foodie.payment.core.domain.valueobject;

import com.mohamedbamoh.foodie.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditHistoryId extends BaseId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}
