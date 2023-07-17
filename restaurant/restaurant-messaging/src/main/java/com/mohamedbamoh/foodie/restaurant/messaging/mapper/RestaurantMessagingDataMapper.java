package com.mohamedbamoh.foodie.restaurant.messaging.mapper;

import com.mohamedbamoh.foodie.common.domain.valueobject.ProductId;
import com.mohamedbamoh.foodie.common.domain.valueobject.RestaurantOrderStatus;
import com.mohamedbamoh.foodie.kafka.order.avro.model.OrderApprovalStatus;
import com.mohamedbamoh.foodie.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.mohamedbamoh.foodie.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import com.mohamedbamoh.foodie.restaurant.domain.core.entity.Product;
import com.mohamedbamoh.foodie.restaurant.domain.core.event.OrderApprovedEvent;
import com.mohamedbamoh.foodie.restaurant.domain.core.event.OrderRejectedEvent;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.dto.RestaurantApprovalRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantMessagingDataMapper {

    public RestaurantApprovalResponseAvroModel orderApprovedConverter(OrderApprovedEvent event) {
        return RestaurantApprovalResponseAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setOrderId(event.getOrderApproval().getOrderId().getValue().toString())
                .setRestaurantId(event.getRestaurantId().getValue().toString())
                .setCreatedAt(event.getCreatedAt().toInstant())
                .setOrderApprovalStatus(OrderApprovalStatus.valueOf(event.getOrderApproval().getOrderApprovalStatus().name()))
                .setFailureMessages(event.getFailureMessages())
                .build();
    }

    public RestaurantApprovalResponseAvroModel orderRejectedConverter(OrderRejectedEvent event) {
        return RestaurantApprovalResponseAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setOrderId(event.getOrderApproval().getOrderId().getValue().toString())
                .setRestaurantId(event.getRestaurantId().getValue().toString())
                .setCreatedAt(event.getCreatedAt().toInstant())
                .setOrderApprovalStatus(OrderApprovalStatus.valueOf(event.getOrderApproval().getOrderApprovalStatus().name()))
                .setFailureMessages(event.getFailureMessages())
                .build();
    }

    public RestaurantApprovalRequest restaurantApprovalConverter(RestaurantApprovalRequestAvroModel request) {
        return RestaurantApprovalRequest.builder()
                .sagaId(request.getSagaId())
                .id(request.getId())
                .restaurantId(request.getRestaurantId())
                .orderId(request.getOrderId())
                .restaurantOrderStatus(RestaurantOrderStatus.valueOf(request.getRestaurantOrderStatus().name()))
                .products(request.getProducts().stream().map(product ->
                        Product.builder()
                                .productId(new ProductId(UUID.fromString(product.getId())))
                                .quantity(product.getQuantity())
                                .build()).collect(Collectors.toList()))
                .price(request.getPrice())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
