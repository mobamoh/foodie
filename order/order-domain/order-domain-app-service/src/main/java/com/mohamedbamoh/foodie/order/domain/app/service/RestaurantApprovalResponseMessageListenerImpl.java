package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.RestaurantApprovalResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.port.input.message.listener.restaurant.RestaurantApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {
    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

    }
}
