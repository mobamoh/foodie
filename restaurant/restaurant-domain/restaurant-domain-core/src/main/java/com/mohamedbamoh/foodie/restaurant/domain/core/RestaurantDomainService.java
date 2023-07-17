package com.mohamedbamoh.foodie.restaurant.domain.core;

import com.mohamedbamoh.foodie.common.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.restaurant.domain.core.entity.Restaurant;
import com.mohamedbamoh.foodie.restaurant.domain.core.event.OrderApprovalEvent;
import com.mohamedbamoh.foodie.restaurant.domain.core.event.OrderApprovedEvent;
import com.mohamedbamoh.foodie.restaurant.domain.core.event.OrderRejectedEvent;

import java.util.List;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(Restaurant restaurant,
                                     List<String> failureMessages,
                                     DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
                                     DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);

}
