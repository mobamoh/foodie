package com.mohamedbamoh.foodie.payment.data.access.payment.mapper;

import com.mohamedbamoh.foodie.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;
import com.mohamedbamoh.foodie.payment.core.domain.valueobject.PaymentId;
import com.mohamedbamoh.foodie.payment.data.access.payment.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentDataAccessMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId().getValue())
                .customerId(payment.getCustomerId().getValue())
                .orderId(payment.getOrderId().getValue())
                .price(payment.getPrice().getAmount())
                .status(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(new PaymentId(paymentEntity.getId()))
                .customerId(new CustomerId(paymentEntity.getCustomerId()))
                .orderId(new OrderId(paymentEntity.getOrderId()))
                .price(new Money(paymentEntity.getPrice()))
                .createdAt(paymentEntity.getCreatedAt())
                .build();
    }
}
