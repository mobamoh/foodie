package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.RestaurantApprovalResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.port.input.message.listener.restaurant.RestaurantApprovalResponseMessageListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {

    private final OrderApprovalSaga orderApprovalSaga;

    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        orderApprovalSaga.process(restaurantApprovalResponse);
        log.info("Order: {} is approved", restaurantApprovalResponse.getOrderId());
    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {
        var orderCancelledEvent = orderApprovalSaga.rollback(restaurantApprovalResponse);
        log.info("Publishing OrderCancelledEvent for order: {} with failure messages: {}",
                restaurantApprovalResponse.getOrderId(),
                String.join(",", restaurantApprovalResponse.getFailureMessages()));
        orderCancelledEvent.fire();
    }
}
