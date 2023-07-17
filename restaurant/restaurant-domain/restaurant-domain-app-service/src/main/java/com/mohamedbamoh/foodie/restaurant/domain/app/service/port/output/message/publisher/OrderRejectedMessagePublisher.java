package com.mohamedbamoh.foodie.restaurant.domain.app.service.port.output.message.publisher;

import com.mohamedbamoh.foodie.common.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.restaurant.domain.core.event.OrderRejectedEvent;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {
}
