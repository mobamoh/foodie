package com.mohamedbamoh.foodie.order.domain.app.service.port.input.message.listener.restaurant;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
