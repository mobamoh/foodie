package com.mohamedbamoh.foodie.customer.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;
import lombok.Getter;

@Getter
public class Customer extends AggregateRoot<CustomerId> {
    private final String username;
    private final String firstName;
    private final String lastName;

    public Customer(CustomerId customerId, String username, String firstName, String lastName) {
        super.setId(customerId);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
