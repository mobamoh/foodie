package com.mohamedbamoh.foodie.common.data.access.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@IdClass(RestaurantEntityId.class)
@Table(name = "order_restaurant_m_view", schema = "restaurant")
@Entity
public class RestaurantEntity {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    @Id
    @EqualsAndHashCode.Include
    private UUID productId;

    private String name;
    private Boolean active;
    private String productName;
    private BigDecimal price;
    private Boolean productAvailable;
}
