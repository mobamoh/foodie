package com.mohamedbamoh.foodie.restaurant.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.common.domain.valueobject.Money;
import com.mohamedbamoh.foodie.common.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.common.domain.valueobject.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderDetail extends BaseEntity<OrderId> {

    private final OrderStatus orderStatus;
    private final Money totalAmount;
    private final List<Product> products;

    @Builder
    public OrderDetail(OrderId orderId, OrderStatus orderStatus, Money totalAmount, List<Product> products) {
        super.setId(orderId);
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.products = products;
    }
}
