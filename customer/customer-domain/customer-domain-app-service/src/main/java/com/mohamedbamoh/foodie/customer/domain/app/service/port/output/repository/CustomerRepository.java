package com.mohamedbamoh.foodie.customer.domain.app.service.port.output.repository;

import com.mohamedbamoh.foodie.customer.domain.core.entity.Customer;

public interface CustomerRepository {
    Customer createCustomer(Customer customer);
}
