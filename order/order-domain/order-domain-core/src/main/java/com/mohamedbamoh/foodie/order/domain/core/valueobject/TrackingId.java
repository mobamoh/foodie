package com.mohamedbamoh.foodie.order.domain.core.valueobject;

import com.mohamedbamoh.foodie.common.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
