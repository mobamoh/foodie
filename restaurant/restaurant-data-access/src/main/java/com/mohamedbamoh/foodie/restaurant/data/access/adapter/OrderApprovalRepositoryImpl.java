package com.mohamedbamoh.foodie.restaurant.data.access.adapter;

import com.mohamedbamoh.foodie.restaurant.domain.core.entity.OrderApproval;
import com.mohamedbamoh.foodie.restaurant.data.access.mapper.RestaurantDataAccessMapper;
import com.mohamedbamoh.foodie.restaurant.data.access.repository.OrderApprovalJpaRepository;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.port.output.repository.OrderApprovalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        return restaurantDataAccessMapper
                .orderApprovalEntityToOrderApproval(orderApprovalJpaRepository
                        .save(restaurantDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval)));
    }
}
