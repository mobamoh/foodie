package com.mohamedbamoh.foodie.restaurant.domain.core.valueobject;

import com.mohamedbamoh.foodie.common.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {
    public OrderApprovalId(UUID value) {
        super(value);
    }
}
