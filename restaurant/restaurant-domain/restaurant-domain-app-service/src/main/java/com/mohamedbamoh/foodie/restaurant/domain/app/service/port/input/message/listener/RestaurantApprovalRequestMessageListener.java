package com.mohamedbamoh.foodie.restaurant.domain.app.service.port.input.message.listener;

import com.mohamedbamoh.foodie.restaurant.domain.app.service.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {
    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}
