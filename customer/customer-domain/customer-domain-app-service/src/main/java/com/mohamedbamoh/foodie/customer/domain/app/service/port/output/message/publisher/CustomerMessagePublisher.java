package com.mohamedbamoh.foodie.customer.domain.app.service.port.output.message.publisher;

import com.mohamedbamoh.foodie.customer.domain.core.event.CustomerCreatedEvent;

public interface CustomerMessagePublisher {
    void publish(CustomerCreatedEvent customerCreatedEvent);
}
