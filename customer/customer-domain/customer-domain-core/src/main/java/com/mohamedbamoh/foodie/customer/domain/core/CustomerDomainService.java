package com.mohamedbamoh.foodie.customer.domain.core;

import com.mohamedbamoh.foodie.customer.domain.core.entity.Customer;
import com.mohamedbamoh.foodie.customer.domain.core.event.CustomerCreatedEvent;

public interface CustomerDomainService {

    CustomerCreatedEvent validateAndInitiateCustomer(Customer customer);
}
