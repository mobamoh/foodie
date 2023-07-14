package com.mohamedbamoh.foodie.restaurant.core.domain;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.domain.valueobject.OrderApprovalStatus;
import com.mohamedbamoh.foodie.restaurant.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.restaurant.core.domain.event.OrderApprovalEvent;
import com.mohamedbamoh.foodie.restaurant.core.domain.event.OrderApprovedEvent;
import com.mohamedbamoh.foodie.restaurant.core.domain.event.OrderRejectedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.mohamedbamoh.foodie.domain.DomainConstants.UTC;

@Slf4j
public class RestaurantDomainServiceImpl implements RestaurantDomainService {
    @Override
    public OrderApprovalEvent validateOrder(Restaurant restaurant, List<String> failureMessages, DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher, DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher) {
        restaurant.validateOrder(failureMessages);
        log.info("Validating order: {}", restaurant.getOrderDetail().getId().getValue());
        if (failureMessages.isEmpty()) {
            log.info("Order: {} approved", restaurant.getOrderDetail().getId().getValue());
            restaurant.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(restaurant.getOrderApproval(), restaurant.getId(),
                    failureMessages, ZonedDateTime.now(ZoneId.of(UTC)), orderApprovedEventDomainEventPublisher);
        }

        log.info("Order: {} rejected", restaurant.getOrderDetail().getId().getValue());
        restaurant.constructOrderApproval(OrderApprovalStatus.REJECTED);
        return new OrderRejectedEvent(restaurant.getOrderApproval(), restaurant.getId(),
                failureMessages, ZonedDateTime.now(ZoneId.of(UTC)), orderRejectedEventDomainEventPublisher);

    }
}
