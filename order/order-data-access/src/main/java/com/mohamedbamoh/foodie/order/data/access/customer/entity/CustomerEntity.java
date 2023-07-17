package com.mohamedbamoh.foodie.order.data.access.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "order_customer_m_view", schema = "customer")
@Entity
public class CustomerEntity {

    @Id
    private UUID id;
}
