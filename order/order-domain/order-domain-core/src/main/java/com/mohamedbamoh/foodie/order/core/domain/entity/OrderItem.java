package com.mohamedbamoh.foodie.order.core.domain.entity;

import com.mohamedbamoh.foodie.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.OrderItemId;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderItemId orderItemId;
    private OrderId orderId;
    private final Product product;
    private final Integer quantity;
    private final Money price;
    private final Money subTotal;

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
