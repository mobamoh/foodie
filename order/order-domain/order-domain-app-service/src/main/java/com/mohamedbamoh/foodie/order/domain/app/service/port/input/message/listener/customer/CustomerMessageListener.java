package com.mohamedbamoh.foodie.order.domain.app.service.port.input.message.listener.customer;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.CustomerModel;

public interface CustomerMessageListener {
    void customerCreated(CustomerModel customerModel);
}
