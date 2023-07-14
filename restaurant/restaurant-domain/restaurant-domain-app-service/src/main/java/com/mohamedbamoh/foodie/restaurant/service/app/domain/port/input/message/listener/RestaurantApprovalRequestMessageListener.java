package com.mohamedbamoh.foodie.restaurant.service.app.domain.port.input.message.listener;

import com.mohamedbamoh.foodie.restaurant.service.app.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {
    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}
