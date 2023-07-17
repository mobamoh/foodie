package com.mohamedbamoh.foodie.order.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    public Customer() {
    }

    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
