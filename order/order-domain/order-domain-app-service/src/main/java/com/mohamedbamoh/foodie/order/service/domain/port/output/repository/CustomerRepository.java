package com.mohamedbamoh.foodie.order.service.domain.port.output.repository;

import com.mohamedbamoh.foodie.order.core.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findCustomer(UUID customerId);
}
