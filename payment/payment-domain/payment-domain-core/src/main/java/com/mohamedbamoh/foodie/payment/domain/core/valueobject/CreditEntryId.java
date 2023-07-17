package com.mohamedbamoh.foodie.payment.domain.core.valueobject;

import com.mohamedbamoh.foodie.common.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditEntryId extends BaseId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
