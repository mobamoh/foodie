package com.mohamedbamoh.foodie.order.data.access.customer.mapper;

import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.order.domain.core.entity.Customer;
import com.mohamedbamoh.foodie.order.data.access.customer.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

    public Customer customerToCustomerEntity(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
