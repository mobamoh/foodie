package com.mohamedbamoh.foodie.payment.domain.core.valueobject;

import com.mohamedbamoh.foodie.common.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditHistoryId extends BaseId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}
