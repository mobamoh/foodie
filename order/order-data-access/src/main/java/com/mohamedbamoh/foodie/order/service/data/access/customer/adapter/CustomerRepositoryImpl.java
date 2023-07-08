package com.mohamedbamoh.foodie.order.service.data.access.customer.adapter;

import com.mohamedbamoh.foodie.order.core.domain.entity.Customer;
import com.mohamedbamoh.foodie.order.service.data.access.customer.mapper.CustomerDataAccessMapper;
import com.mohamedbamoh.foodie.order.service.data.access.customer.repository.CutomerJpaRepository;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CutomerJpaRepository cutomerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;
    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return Optional.empty();
    }
}
