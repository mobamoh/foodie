package com.mohamedbamoh.foodie.payment.service.messaging.mapper;


import com.mohamedbamoh.foodie.domain.valueobject.PaymentOrderStatus;
import com.mohamedbamoh.foodie.kafka.order.avro.model.PaymentRequestAvroModel;
import com.mohamedbamoh.foodie.kafka.order.avro.model.PaymentResponseAvroModel;
import com.mohamedbamoh.foodie.kafka.order.avro.model.PaymentStatus;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentCancelledEvent;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentCompletedEvent;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentFailedEvent;
import com.mohamedbamoh.foodie.payment.service.domain.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {

    public PaymentResponseAvroModel paymentCompletedEventToPaymentResponseAvroModel(PaymentCompletedEvent paymentCompletedEvent) {
        var payment = paymentCompletedEvent.getPayment();
        return PaymentResponseAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setPaymentId(payment.getId().getValue().toString())
                .setCustomerId(payment.getCustomerId().getValue().toString())
                .setOrderId(payment.getOrderId().getValue().toString())
                .setPrice(payment.getPrice().getAmount())
                .setPaymentStatus(PaymentStatus.valueOf(payment.getPaymentStatus().name()))
                .setCreatedAt(paymentCompletedEvent.getCreatedAt().toInstant())
                .setFailureMessages(paymentCompletedEvent.getFailureMessages())
                .build();
    }

    public PaymentResponseAvroModel paymentCancelledEventToPaymentResponseAvroModel(PaymentCancelledEvent paymentCancelledEvent) {
        var payment = paymentCancelledEvent.getPayment();
        return PaymentResponseAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setPaymentId(payment.getId().getValue().toString())
                .setCustomerId(payment.getCustomerId().getValue().toString())
                .setOrderId(payment.getOrderId().getValue().toString())
                .setPrice(payment.getPrice().getAmount())
                .setPaymentStatus(PaymentStatus.valueOf(payment.getPaymentStatus().name()))
                .setCreatedAt(paymentCancelledEvent.getCreatedAt().toInstant())
                .setFailureMessages(paymentCancelledEvent.getFailureMessages())
                .build();
    }

    public PaymentResponseAvroModel paymentFailedEventToPaymentResponseAvroModel(PaymentFailedEvent paymentFailedEvent) {
        var payment = paymentFailedEvent.getPayment();
        return PaymentResponseAvroModel.newBuilder()
                .setSagaId("")
                .setId(UUID.randomUUID().toString())
                .setPaymentId(payment.getId().getValue().toString())
                .setCustomerId(payment.getCustomerId().getValue().toString())
                .setOrderId(payment.getOrderId().getValue().toString())
                .setPrice(payment.getPrice().getAmount())
                .setPaymentStatus(PaymentStatus.valueOf(payment.getPaymentStatus().name()))
                .setCreatedAt(paymentFailedEvent.getCreatedAt().toInstant())
                .setFailureMessages(paymentFailedEvent.getFailureMessages())
                .build();
    }

    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .sagaId("")
                .id(paymentRequestAvroModel.getId())
                .customerId(paymentRequestAvroModel.getCustomerId())
                .orderId(paymentRequestAvroModel.getOrderId())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestAvroModel.getPaymentOrderStatus().name()))
                .build();
    }
}
