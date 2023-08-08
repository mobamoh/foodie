package com.mohamedbamoh.foodie.customer.domain.app.service;

import com.mohamedbamoh.foodie.customer.domain.app.service.dto.create.CreateCustomerCommand;
import com.mohamedbamoh.foodie.customer.domain.app.service.dto.create.CreateCustomerResponse;
import com.mohamedbamoh.foodie.customer.domain.app.service.mapper.CustomerDataMapper;
import com.mohamedbamoh.foodie.customer.domain.app.service.port.input.service.CustomerApplicationService;
import com.mohamedbamoh.foodie.customer.domain.app.service.port.output.message.publisher.CustomerMessagePublisher;
import com.mohamedbamoh.foodie.customer.domain.core.event.CustomerCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

    private final CustomerCreateCommandHandler customerCreateCommandHandler;
    private final CustomerDataMapper customerDataMapper;
    private final CustomerMessagePublisher customerMessagePublisher;

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerCommand createCustomerCommand) {
        CustomerCreatedEvent customerCreatedEvent = customerCreateCommandHandler.createCustomer(createCustomerCommand);
        customerMessagePublisher.publish(customerCreatedEvent);
        return customerDataMapper
                .customerToCreateCustomerResponse(customerCreatedEvent.getCustomer(),
                        "Customer saved successfully!");
    }
}
