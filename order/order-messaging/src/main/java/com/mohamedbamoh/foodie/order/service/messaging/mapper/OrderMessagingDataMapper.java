package com.mohamedbamoh.foodie.order.service.messaging.mapper;

import com.mohamedbamoh.foodie.domain.valueobject.OrderApprovalStatus;
import com.mohamedbamoh.foodie.domain.valueobject.PaymentStatus;
import com.mohamedbamoh.foodie.kafka.order.avro.model.*;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderCancalledEvent;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderCreatedEvent;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderPaidEvent;
import com.mohamedbamoh.foodie.order.service.domain.dto.message.PaymentResponse;
import com.mohamedbamoh.foodie.order.service.domain.dto.message.RestaurantApprovalResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {
        var order = orderCreatedEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setOrderId(order.getId().getValue().toString())
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }

    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancalledEvent orderCancalledEvent) {
        var order = orderCancalledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setCreatedAt(orderCancalledEvent.getCreatedAt().toInstant())
                .setOrderId(order.getId().getValue().toString())
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();
    }

    public RestaurantApprovalRequestAvroModel orderPaidToRestaurantApprovalRequestAvroModel(OrderPaidEvent domainEvent) {
        var order = domainEvent.getOrder();
        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setCreatedAt(domainEvent.getCreatedAt().toInstant())
                .setOrderId(order.getId().getValue().toString())
                .setRestaurantId(order.getRestaurantId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
                .setProducts(order.getItems().stream().map(item ->
                        Product.newBuilder()
                                .setId(item.getProduct().getId().getValue().toString())
                                .setQuantity(item.getQuantity())
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .sagaId("")
                .id(paymentResponseAvroModel.getId())
                .paymentId(paymentResponseAvroModel.getPaymentId())
                .orderId(paymentResponseAvroModel.getOrderId())
                .customerId(paymentResponseAvroModel.getCustomerId())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public RestaurantApprovalResponse approvalResponseAvroModelToApprovalResponse(RestaurantApprovalResponseAvroModel restaurantApprovalResponseAvroModel) {
        return RestaurantApprovalResponse.builder()
                .sagaId("")
                .id(restaurantApprovalResponseAvroModel.getId())
                .restaurantId(restaurantApprovalResponseAvroModel.getRestaurantId())
                .orderId(restaurantApprovalResponseAvroModel.getOrderId())
                .createdAt(restaurantApprovalResponseAvroModel.getCreatedAt())
                .orderApprovalStatus(OrderApprovalStatus.valueOf(restaurantApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(restaurantApprovalResponseAvroModel.getFailureMessages())
                .build();
    }
}
