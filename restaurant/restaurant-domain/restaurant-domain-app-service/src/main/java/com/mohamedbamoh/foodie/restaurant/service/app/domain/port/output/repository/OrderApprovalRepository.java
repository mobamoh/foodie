package com.mohamedbamoh.foodie.restaurant.service.app.domain.port.output.repository;

import com.mohamedbamoh.foodie.restaurant.core.domain.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}
