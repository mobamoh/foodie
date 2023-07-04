package com.mohamedbamoh.foodie.order.service.domain.port.input.message.listener.restaurant;

import com.mohamedbamoh.foodie.order.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
