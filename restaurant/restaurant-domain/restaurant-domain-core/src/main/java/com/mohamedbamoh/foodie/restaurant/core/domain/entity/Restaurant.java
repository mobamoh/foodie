package com.mohamedbamoh.foodie.restaurant.core.domain.entity;

import com.mohamedbamoh.foodie.domain.entity.AggregateRoot;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.domain.valueobject.OrderApprovalStatus;
import com.mohamedbamoh.foodie.domain.valueobject.OrderStatus;
import com.mohamedbamoh.foodie.domain.valueobject.RestaurantId;
import com.mohamedbamoh.foodie.restaurant.core.domain.valueobject.OrderApprovalId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {

    private OrderApproval orderApproval;
    @Setter
    private Boolean active;
    private final OrderDetail orderDetail;

    @Builder
    public Restaurant(RestaurantId restaurantId, OrderApproval orderApproval, Boolean active, OrderDetail orderDetail) {
        super.setId(restaurantId);
        this.orderApproval = orderApproval;
        this.active = active;
        this.orderDetail = orderDetail;
    }

    public void validateOrder(List<String> failureMessages) {
        if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add(String.format("Pament is not completed for order: %s",
                    orderDetail.getId().getValue()));
        }

        var totalAmount = orderDetail.getProducts().stream().map(product -> {
            if (!product.getAvailable()) {
                failureMessages.add(String.format("Product: %s is not available", product.getId().getValue()));
            }
            return product.getPrice().multiply(product.getQuantity());
        }).reduce(Money.ZERO, Money::add);

        if (totalAmount.equals(orderDetail.getTotalAmount())) {
            failureMessages.add(String.format("Total price is not correct for order: %s", orderDetail.getId().getValue()));
        }
    }

    public void constructOrderApproval(OrderApprovalStatus orderApprovalStatus) {
        this.orderApproval = OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(UUID.randomUUID()))
                .orderId(this.orderDetail.getId())
                .restaurantId(this.getId())
                .orderApprovalStatus(orderApprovalStatus)
                .build();
    }
}
