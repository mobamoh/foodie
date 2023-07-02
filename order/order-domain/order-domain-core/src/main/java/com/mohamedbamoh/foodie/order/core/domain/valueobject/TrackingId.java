package com.mohamedbamoh.foodie.order.core.domain.valueobject;

import com.mohamedbamoh.foodie.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
