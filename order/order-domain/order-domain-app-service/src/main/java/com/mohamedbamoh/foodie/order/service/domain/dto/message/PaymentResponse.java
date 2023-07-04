package com.mohamedbamoh.foodie.order.service.domain.dto.message;

import com.mohamedbamoh.foodie.domain.valueobject.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@NoArgsConstructor
@Getter
public class PaymentResponse {

    private String orderId;
    private String sagaId;
    private String paymentId;
    private String customerId;
    private BigDecimal price;
    private Instant createdAt;
    private PaymentStatus paymentStatus;
    private List<String> failureMessages;

}
