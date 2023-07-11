package com.mohamedbamoh.foodie.payment.data.access.creditentry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_entry")
@Entity
public class CreditEntryEntity {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID customerId;
    private BigDecimal totalCreditAmount;
}
