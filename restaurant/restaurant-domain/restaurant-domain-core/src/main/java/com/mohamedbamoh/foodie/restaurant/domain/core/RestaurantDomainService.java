package com.mohamedbamoh.foodie.restaurant.domain.core;

import com.mohamedbamoh.foodie.restaurant.domain.core.entity.Restaurant;
import com.mohamedbamoh.foodie.restaurant.domain.core.event.OrderApprovalEvent;

import java.util.List;

public interface RestaurantDomainService {

//    OrderApprovalEvent validateOrder(Restaurant restaurant,
//                                     List<String> failureMessages,
//                                     DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
//                                     DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);

    OrderApprovalEvent validateOrder(Restaurant restaurant,
                                     List<String> failureMessages);

}
