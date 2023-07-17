package com.mohamedbamoh.foodie.order.data.access.order.mapper;

import com.mohamedbamoh.foodie.common.domain.valueobject.*;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import com.mohamedbamoh.foodie.order.domain.core.entity.OrderItem;
import com.mohamedbamoh.foodie.order.domain.core.entity.Product;
import com.mohamedbamoh.foodie.order.domain.core.valueobject.OrderItemId;
import com.mohamedbamoh.foodie.order.domain.core.valueobject.StreetAddress;
import com.mohamedbamoh.foodie.order.domain.core.valueobject.TrackingId;
import com.mohamedbamoh.foodie.order.data.access.order.entity.OrderAddressEntity;
import com.mohamedbamoh.foodie.order.data.access.order.entity.OrderEntity;
import com.mohamedbamoh.foodie.order.data.access.order.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        var orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ?
                        String.join(",", order.getFailureMessages())
                        : "")
                .build();

        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(item -> item.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages().split(",")))
                )
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream()
                .map(item -> OrderItem.builder()
                        .orderItemId(new OrderItemId(item.getId()))
                        .product(new Product(new ProductId(item.getProductId())))
                        .quantity(item.getQuantity())
                        .price(new Money(item.getPrice()))
                        .subTotal(new Money(item.getSubTotal()))
                        .build()
                ).collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(address.getId(), address.getStreet(),
                address.getPostalCode(), address.getCity());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream().map(item -> OrderItemEntity.builder()
                .id(item.getId().getValue())
                .productId(item.getProduct().getId().getValue())
                .price(item.getPrice().getAmount())
                .quantity(item.getQuantity())
                .subTotal(item.getSubTotal().getAmount())
                .build()
        ).collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getId())
                .city(deliveryAddress.getCity())
                .postalCode(deliveryAddress.getPostalCode())
                .street(deliveryAddress.getStreet())
                .build();
    }
}
