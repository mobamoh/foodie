package com.mohamedbamoh.foodie.restaurant.data.access.mapper;

import com.mohamedbamoh.foodie.common.data.access.restaurant.entity.RestaurantEntity;
import com.mohamedbamoh.foodie.common.data.access.restaurant.exception.RestaurantDataAccessException;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.domain.valueobject.ProductId;
import com.mohamedbamoh.foodie.domain.valueobject.RestaurantId;
import com.mohamedbamoh.foodie.restaurant.core.domain.entity.OrderApproval;
import com.mohamedbamoh.foodie.restaurant.core.domain.entity.OrderDetail;
import com.mohamedbamoh.foodie.restaurant.core.domain.entity.Product;
import com.mohamedbamoh.foodie.restaurant.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.restaurant.core.domain.valueobject.OrderApprovalId;
import com.mohamedbamoh.foodie.restaurant.data.access.entity.OrderApprovalEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getOrderDetail().getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {

        var restaurantEntity = restaurantEntities.stream().findFirst()
                .orElseThrow(() -> new RestaurantDataAccessException("No restaurants found!"));

        var restaurantProducts = restaurantEntities.stream().map(entity ->
                        Product.builder()
                                .productId(new ProductId(entity.getProductId()))
                                .name(entity.getProductName())
                                .price(new Money(entity.getPrice()))
                                .available(entity.getProductAvailable())
                                .build())
                .collect(Collectors.toList());

        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getId()))
                .orderDetail(OrderDetail.builder()
                        .products(restaurantProducts)
                        .build())
                .active(restaurantEntity.getActive())
                .build();
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .id(orderApproval.getId().getValue())
                .restaurantId(orderApproval.getRestaurantId().getValue())
                .orderId(orderApproval.getOrderId().getValue())
                .status(orderApproval.getOrderApprovalStatus())
                .build();
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(orderApprovalEntity.getId()))
                .restaurantId(new RestaurantId(orderApprovalEntity.getRestaurantId()))
                .orderId(new OrderId(orderApprovalEntity.getOrderId()))
                .orderApprovalStatus(orderApprovalEntity.getStatus())
                .build();
    }
}
