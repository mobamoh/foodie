package com.mohamedbamoh.foodie.order.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;
import lombok.Getter;

@Getter
public class Customer extends AggregateRoot<CustomerId> {

    private String username;
    private String firstName;
    private String lastName;

    public Customer(CustomerId customerId, String username, String firstName, String lastName) {
        super.setId(customerId);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
