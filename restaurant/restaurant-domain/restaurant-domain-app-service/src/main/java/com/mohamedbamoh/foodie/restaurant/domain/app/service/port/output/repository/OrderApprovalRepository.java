package com.mohamedbamoh.foodie.restaurant.domain.app.service.port.output.repository;

import com.mohamedbamoh.foodie.restaurant.domain.core.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}
