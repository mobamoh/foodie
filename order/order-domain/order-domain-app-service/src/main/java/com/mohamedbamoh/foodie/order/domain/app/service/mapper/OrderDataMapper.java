package com.mohamedbamoh.foodie.order.domain.app.service.mapper;

import com.mohamedbamoh.foodie.common.domain.valueobject.*;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.CreateOrderResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.OrderAddress;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.track.TrackOrderResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval.OrderApprovalEventPayload;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval.OrderApprovalEventProduct;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.payment.OrderPaymentEventPayload;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import com.mohamedbamoh.foodie.order.domain.core.entity.OrderItem;
import com.mohamedbamoh.foodie.order.domain.core.entity.Product;
import com.mohamedbamoh.foodie.order.domain.core.entity.Restaurant;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCreatedEvent;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderPaidEvent;
import com.mohamedbamoh.foodie.order.domain.core.valueobject.StreetAddress;
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
            List<com.mohamedbamoh.foodie.order.domain.app.service.dto.create.OrderItem> items) {
        return items.stream().map(item -> OrderItem.builder()
                .product(new Product(new ProductId(item.getProductId())))
                .quantity(item.getQuantity())
                .price(new Money(item.getPrice()))
                .subTotal(new Money(item.getSubTotal()))
                .build()).collect(Collectors.toList());

    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(UUID.randomUUID(), address.getStreet(),
                address.getPostalCode(), address.getCity());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .trackingId(order.getTrackingId().getValue())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    public OrderPaymentEventPayload orderCreatedEventToOrderPaymentEventPayload(OrderCreatedEvent orderCreatedEvent) {
        return OrderPaymentEventPayload.builder()
                .customerId(orderCreatedEvent.getOrder().getCustomerId().getValue().toString())
                .orderId(orderCreatedEvent.getOrder().getId().getValue().toString())
                .price(orderCreatedEvent.getOrder().getPrice().getAmount())
                .createdAt(orderCreatedEvent.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.PENDING.name())
                .build();
    }

    public OrderApprovalEventPayload orderPaidEventToOrderApprovalEventPayload(OrderPaidEvent orderPaidEvent) {
        return OrderApprovalEventPayload.builder()
                .orderId(orderPaidEvent.getOrder().getId().getValue().toString())
                .restaurantId(orderPaidEvent.getOrder().getRestaurantId().getValue().toString())
                .restaurantOrderStatus(RestaurantOrderStatus.PAID.name())
                .products(orderPaidEvent.getOrder().getItems().stream().map(item ->
                        OrderApprovalEventProduct.builder()
                                .id(item.getProduct().getId().getValue().toString())
                                .quantity(item.getQuantity())
                                .build()).collect(Collectors.toList()))
                .price(orderPaidEvent.getOrder().getPrice().getAmount())
                .createdAt(orderPaidEvent.getCreatedAt())
                .build();
    }
}
