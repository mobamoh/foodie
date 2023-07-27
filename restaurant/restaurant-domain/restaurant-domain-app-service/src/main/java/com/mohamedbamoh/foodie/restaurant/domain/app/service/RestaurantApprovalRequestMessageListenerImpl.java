package com.mohamedbamoh.foodie.restaurant.domain.app.service;

import com.mohamedbamoh.foodie.restaurant.domain.app.service.dto.RestaurantApprovalRequest;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.port.input.message.listener.RestaurantApprovalRequestMessageListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantApprovalRequestMessageListenerImpl implements RestaurantApprovalRequestMessageListener {

    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;

    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {
        restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
//        orderApprovalEvent.fire();
    }
}
