package com.mohamedbamoh.foodie.order.service.data.access.restaurant.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RestaurantEntityId implements Serializable {
    private UUID id;
    private UUID productId;
}
