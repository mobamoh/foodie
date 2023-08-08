package com.mohamedbamoh.foodie.customer.data.access.adapter;

import com.mohamedbamoh.foodie.customer.data.access.mapper.CustomerDataAccessMapper;
import com.mohamedbamoh.foodie.customer.data.access.repository.CustomerJpaRepository;
import com.mohamedbamoh.foodie.customer.domain.app.service.port.output.repository.CustomerRepository;
import com.mohamedbamoh.foodie.customer.domain.core.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository,
                                  CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerDataAccessMapper.customerEntityToCustomer(
                customerJpaRepository.save(customerDataAccessMapper.customerToCustomerEntity(customer)));
    }
}
