package com.mohamedbamoh.foodie.customer.domain.core.event;

import com.mohamedbamoh.foodie.common.domain.event.DomainEvent;
import com.mohamedbamoh.foodie.customer.domain.core.entity.Customer;
import lombok.Getter;

import java.time.ZonedDateTime;

public class CustomerCreatedEvent implements DomainEvent<Customer> {

    @Getter
    private final Customer customer;
    private final ZonedDateTime createdAt;

    public CustomerCreatedEvent(Customer customer, ZonedDateTime createdAt) {
        this.customer = customer;
        this.createdAt = createdAt;
    }

}