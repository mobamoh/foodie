package com.mohamedbamoh.foodie.restaurant.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.common.domain.valueobject.OrderApprovalStatus;
import com.mohamedbamoh.foodie.common.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.common.domain.valueobject.RestaurantId;
import com.mohamedbamoh.foodie.restaurant.domain.core.valueobject.OrderApprovalId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderApproval extends BaseEntity<OrderApprovalId> {

    private final RestaurantId restaurantId;
    private final OrderId orderId;
    private final OrderApprovalStatus orderApprovalStatus;

    @Builder
    public OrderApproval(OrderApprovalId orderApprovalId, RestaurantId restaurantId, OrderId orderId, OrderApprovalStatus orderApprovalStatus) {
        super.setId(orderApprovalId);
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.orderApprovalStatus = orderApprovalStatus;
    }
}
