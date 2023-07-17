package com.mohamedbamoh.foodie.order.domain.core.valueobject;

import com.mohamedbamoh.foodie.common.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
