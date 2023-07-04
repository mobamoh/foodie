package com.mohamedbamoh.foodie.order.service.domain.mapper;

import com.mohamedbamoh.foodie.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.domain.valueobject.ProductId;
import com.mohamedbamoh.foodie.domain.valueobject.RestaurantId;
import com.mohamedbamoh.foodie.order.core.domain.entity.Order;
import com.mohamedbamoh.foodie.order.core.domain.entity.OrderItem;
import com.mohamedbamoh.foodie.order.core.domain.entity.Product;
import com.mohamedbamoh.foodie.order.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.StreetAddress;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderResponse;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.OrderAddress;
import com.mohamedbamoh.foodie.order.service.domain.dto.track.TrackOrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(item ->
                        new Product(new ProductId(item.getProductId()))
                ).collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<com.mohamedbamoh.foodie.order.service.domain.dto.create.OrderItem> items) {
        return items.stream().map(item -> OrderItem.builder()
                .product(new Product(new ProductId(item.getProductId())))
                .price(new Money(item.getPrice()))
                .subTotal(new Money(item.getSubTotal()))
                .build()).collect(Collectors.toList());

    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(UUID.randomUUID(), address.getStreet(),
                address.getPostalCode(), address.getCity());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .trackingId(order.getTrackingId().getValue())
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }
}
