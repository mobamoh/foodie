package com.mohamedbamoh.foodie.order.domain.app.service.dto.message;

import com.mohamedbamoh.foodie.common.domain.valueobject.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class RestaurantApprovalResponse {

    private String id;
    private String sagaId;
    private String orderId;
    private String restaurantId;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failureMessages;


}
