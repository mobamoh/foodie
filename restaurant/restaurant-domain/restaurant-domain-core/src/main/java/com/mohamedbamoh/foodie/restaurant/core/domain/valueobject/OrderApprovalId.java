package com.mohamedbamoh.foodie.restaurant.core.domain.valueobject;

import com.mohamedbamoh.foodie.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {
    public OrderApprovalId(UUID value) {
        super(value);
    }
}
