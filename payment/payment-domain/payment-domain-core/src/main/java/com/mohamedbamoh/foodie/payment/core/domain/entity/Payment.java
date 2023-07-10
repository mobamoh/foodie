package com.mohamedbamoh.foodie.payment.core.domain.entity;

import com.mohamedbamoh.foodie.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.domain.valueobject.PaymentStatus;
import com.mohamedbamoh.foodie.payment.core.domain.valueobject.PaymentId;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.mohamedbamoh.foodie.domain.DomainConstants.UTC;

@Getter
public class Payment extends AggregateRoot<PaymentId> {

    private final OrderId orderId;
    private final CustomerId customerId;
    private final Money price;
    private PaymentStatus paymentStatus;
    private ZonedDateTime createdAt;

    @Builder
    private Payment(PaymentId paymentId, OrderId orderId, CustomerId customerId, Money price, PaymentStatus paymentStatus, ZonedDateTime createdAt) {
        super.setId(paymentId);
        this.orderId = orderId;
        this.customerId = customerId;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public void initializePayment() {
        super.setId(new PaymentId(UUID.randomUUID()));
        this.createdAt = ZonedDateTime.now(ZoneId.of(UTC));
    }

    public void validatePayment(List<String> failureMessages) {
        if (price == null || !price.isGreaterThanZero()) {
            failureMessages.add("Total price must be greater than Zero!");
        }
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
