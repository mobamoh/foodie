package com.mohamedbamoh.foodie.restaurant.core.domain;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.restaurant.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.restaurant.core.domain.event.OrderApprovalEvent;
import com.mohamedbamoh.foodie.restaurant.core.domain.event.OrderApprovedEvent;
import com.mohamedbamoh.foodie.restaurant.core.domain.event.OrderRejectedEvent;

import java.util.List;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(Restaurant restaurant,
                                     List<String> failureMessages,
                                     DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
                                     DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);

}
