package com.mohamedbamoh.foodie.customer.domain.app.service.port.input.service;

import com.mohamedbamoh.foodie.customer.domain.app.service.dto.create.CreateCustomerCommand;
import com.mohamedbamoh.foodie.customer.domain.app.service.dto.create.CreateCustomerResponse;
import jakarta.validation.Valid;

public interface CustomerApplicationService {

    CreateCustomerResponse createCustomer(@Valid CreateCustomerCommand createCustomerCommand);
}
