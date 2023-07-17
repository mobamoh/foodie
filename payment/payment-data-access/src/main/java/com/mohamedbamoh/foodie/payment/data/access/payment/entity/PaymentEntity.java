package com.mohamedbamoh.foodie.payment.data.access.payment.entity;

import com.mohamedbamoh.foodie.common.domain.valueobject.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
@Entity
public class PaymentEntity {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID customerId;
    private UUID orderId;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private ZonedDateTime createdAt;
}


