package com.mohamedbamoh.foodie.customer.domain.app.service;

import com.mohamedbamoh.foodie.customer.domain.app.service.dto.create.CreateCustomerCommand;
import com.mohamedbamoh.foodie.customer.domain.app.service.mapper.CustomerDataMapper;
import com.mohamedbamoh.foodie.customer.domain.app.service.port.output.repository.CustomerRepository;
import com.mohamedbamoh.foodie.customer.domain.core.CustomerDomainService;
import com.mohamedbamoh.foodie.customer.domain.core.entity.Customer;
import com.mohamedbamoh.foodie.customer.domain.core.event.CustomerCreatedEvent;
import com.mohamedbamoh.foodie.customer.domain.core.exception.CustomerDomainException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class CustomerCreateCommandHandler {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;
    private final CustomerDataMapper customerDataMapper;


    @Transactional
    public CustomerCreatedEvent createCustomer(CreateCustomerCommand createCustomerCommand) {
        Customer customer = customerDataMapper.createCustomerCommandToCustomer(createCustomerCommand);
        CustomerCreatedEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);
        Customer savedCustomer = customerRepository.createCustomer(customer);
        if (savedCustomer == null) {
            log.error("Could not save customer with id: {}", createCustomerCommand.getCustomerId());
            throw new CustomerDomainException("Could not save customer with id " +
                    createCustomerCommand.getCustomerId());
        }
        log.info("Returning CustomerCreatedEvent for customer id: {}", createCustomerCommand.getCustomerId());
        return customerCreatedEvent;
    }
}
