package com.mohamedbamoh.foodie.restaurant.domain.core.event;

import com.mohamedbamoh.foodie.common.domain.event.DomainEvent;
import com.mohamedbamoh.foodie.common.domain.valueobject.RestaurantId;
import com.mohamedbamoh.foodie.restaurant.domain.core.entity.OrderApproval;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public abstract class OrderApprovalEvent implements DomainEvent<OrderApproval> {
    private final OrderApproval orderApproval;
    private final RestaurantId restaurantId;
    private final List<String> failureMessages;
    private final ZonedDateTime createdAt;
}
