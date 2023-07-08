package com.mohamedbamoh.foodie.order.service.data.access.order.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderItemEntityId implements Serializable {

    private Long id;
    private OrderEntity order;
}
