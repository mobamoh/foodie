package com.mohamedbamoh.foodie.restaurant.domain.app.service.mapper;

import com.mohamedbamoh.foodie.common.domain.valueobject.Money;
import com.mohamedbamoh.foodie.common.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.common.domain.valueobject.OrderStatus;
import com.mohamedbamoh.foodie.common.domain.valueobject.RestaurantId;
import com.mohamedbamoh.foodie.restaurant.domain.core.entity.OrderDetail;
import com.mohamedbamoh.foodie.restaurant.domain.core.entity.Product;
import com.mohamedbamoh.foodie.restaurant.domain.core.entity.Restaurant;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.dto.RestaurantApprovalRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataMapper {

    public Restaurant restaurantApprovalRequestToRestautrant(RestaurantApprovalRequest restaurantApprovalRequest) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(UUID.fromString(restaurantApprovalRequest.getRestaurantId())))
                .orderDetail(OrderDetail.builder()
                        .orderId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())))

                        //TODO check if needs builder
                        .products(restaurantApprovalRequest.getProducts().stream().map(product ->
                                Product.builder()
                                        .productId(product.getId())
                                        .quantity(product.getQuantity())
                                        .build()
                        ).collect(Collectors.toList()))
                        .totalAmount(new Money(restaurantApprovalRequest.getPrice()))
                        .orderStatus(OrderStatus.valueOf(restaurantApprovalRequest.getRestaurantOrderStatus().name()))
                        .build())
                .build();
    }
}
