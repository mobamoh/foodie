package com.mohamedbamoh.foodie.payment.core.domain.valueobject;

import com.mohamedbamoh.foodie.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditEntryId extends BaseId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
