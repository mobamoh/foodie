package com.mohamedbamoh.foodie.order.messaging.mapper;

import com.mohamedbamoh.foodie.common.domain.valueobject.OrderApprovalStatus;
import com.mohamedbamoh.foodie.common.domain.valueobject.PaymentStatus;
import com.mohamedbamoh.foodie.kafka.order.avro.model.*;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval.OrderApprovalEventPayload;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.payment.OrderPaymentEventPayload;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCancelledEvent;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCreatedEvent;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderPaidEvent;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.PaymentResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.RestaurantApprovalResponse;
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

    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
        var order = orderCancelledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
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

    public PaymentRequestAvroModel orderPaymentEventToPaymentRequestAvroModel(String sagaId, OrderPaymentEventPayload
            orderPaymentEventPayload) {
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setCustomerId(orderPaymentEventPayload.getCustomerId())
                .setOrderId(orderPaymentEventPayload.getOrderId())
                .setPrice(orderPaymentEventPayload.getPrice())
                .setCreatedAt(orderPaymentEventPayload.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.valueOf(orderPaymentEventPayload.getPaymentOrderStatus()))
                .build();
    }

    public RestaurantApprovalRequestAvroModel
    orderApprovalEventToRestaurantApprovalRequestAvroModel(String sagaId, OrderApprovalEventPayload
            orderApprovalEventPayload) {
        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setOrderId(orderApprovalEventPayload.getOrderId())
                .setRestaurantId(orderApprovalEventPayload.getRestaurantId())
                .setRestaurantOrderStatus(RestaurantOrderStatus
                        .valueOf(orderApprovalEventPayload.getRestaurantOrderStatus()))
                .setProducts(orderApprovalEventPayload.getProducts().stream().map(orderApprovalEventProduct ->
                        Product.newBuilder()
                                .setId(orderApprovalEventProduct.getId())
                                .setQuantity(orderApprovalEventProduct.getQuantity())
                                .build()).collect(Collectors.toList()))
                .setPrice(orderApprovalEventPayload.getPrice())
                .setCreatedAt(orderApprovalEventPayload.getCreatedAt().toInstant())
                .build();
    }
}
