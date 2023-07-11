package com.mohamedbamoh.foodie.payment.data.access.credithistory.entity;

import com.mohamedbamoh.foodie.payment.core.domain.valueobject.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "credit_history")
@Entity
public class CreditHistoryEntity {

    @EqualsAndHashCode.Include
    @Id
    private UUID id;
    private UUID customerId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

}
