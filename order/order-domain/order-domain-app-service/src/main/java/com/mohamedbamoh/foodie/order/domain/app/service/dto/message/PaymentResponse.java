package com.mohamedbamoh.foodie.order.domain.app.service.dto.message;

import com.mohamedbamoh.foodie.common.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PaymentResponse {

    private String id;
    private String orderId;
    private String sagaId;
    private String paymentId;
    private String customerId;
    private BigDecimal price;
    private Instant createdAt;
    private PaymentStatus paymentStatus;
    private List<String> failureMessages;

}
