package com.mohamedbamoh.foodie.order.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.common.domain.valueobject.Money;
import com.mohamedbamoh.foodie.common.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.order.domain.core.valueobject.OrderItemId;
import lombok.Builder;
import lombok.Getter;


@Getter
public class OrderItem extends BaseEntity<OrderItemId> {

    private OrderId orderId;
    private final Product product;
    private final Integer quantity;
    private final Money price;
    private final Money subTotal;

    @Builder
    private OrderItem(OrderItemId orderItemId, OrderId orderId, Product product, Integer quantity, Money price, Money subTotal) {
        super.setId(orderItemId);
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }

    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        super.setId(orderItemId);
        this.orderId = orderId;
    }

    boolean isPriceValid() {
        return this.price.isGreaterThanZero() &&
                this.price.equals(this.product.getPrice()) &&
                this.price.multiply(this.quantity).equals(subTotal);
    }
}
