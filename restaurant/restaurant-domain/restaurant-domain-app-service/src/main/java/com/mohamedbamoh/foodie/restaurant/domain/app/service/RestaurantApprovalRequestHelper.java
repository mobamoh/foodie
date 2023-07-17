package com.mohamedbamoh.foodie.restaurant.domain.app.service;

import com.mohamedbamoh.foodie.common.domain.valueobject.OrderId;
import com.mohamedbamoh.foodie.restaurant.domain.core.RestaurantDomainService;
import com.mohamedbamoh.foodie.restaurant.domain.core.entity.Restaurant;
import com.mohamedbamoh.foodie.restaurant.domain.core.event.OrderApprovalEvent;
import com.mohamedbamoh.foodie.restaurant.domain.core.exception.RestaurantNotFoundException;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.mapper.RestaurantDataMapper;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.port.output.repository.RestaurantRepository;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.dto.RestaurantApprovalRequest;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.port.output.message.publisher.OrderApprovedMessagePublisher;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.port.output.message.publisher.OrderRejectedMessagePublisher;
import com.mohamedbamoh.foodie.restaurant.domain.app.service.port.output.repository.OrderApprovalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class RestaurantApprovalRequestHelper {
    private final RestaurantDomainService restaurantDomainService;
    private final RestaurantDataMapper restaurantDataMapper;
    private final RestaurantRepository restaurantRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;
    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;

    @Transactional
    public OrderApprovalEvent persistOrderApproval(RestaurantApprovalRequest restaurantApprovalRequest) {
        log.info("Processing request approval for order: {}", restaurantApprovalRequest.getOrderId());
        var failureMessages = new ArrayList<String>();
        var restaurant = findRestaurant(restaurantApprovalRequest);
        var orderApprovalEvent = restaurantDomainService.validateOrder(restaurant, failureMessages, orderApprovedMessagePublisher, orderRejectedMessagePublisher);
        orderApprovalRepository.save(restaurant.getOrderApproval());
        return orderApprovalEvent;
    }

    private Restaurant findRestaurant(RestaurantApprovalRequest restaurantApprovalRequest) {
        var restaurant = restaurantDataMapper.restaurantApprovalRequestToRestautrant(restaurantApprovalRequest);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.error("Restaurant: {} not found", restaurantApprovalRequest.getRestaurantId());
            throw new RestaurantNotFoundException(String.format("Restaurant: %s not found!", restaurantApprovalRequest.getRestaurantId()));
        }
        var restaurantEntity = optionalRestaurant.get();
        restaurant.setActive(restaurantEntity.getActive());
        restaurant.getOrderDetail().getProducts().forEach(product -> {
            restaurantEntity.getOrderDetail().getProducts().forEach(p -> {
                if (p.getId().equals(product.getId())) {
                    product.UpdateWithConfirmedNamePriceAndAvailability(p.getName(), p.getPrice(), p.getAvailable());
                }
            });
        });
        restaurant.getOrderDetail().setId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())));
        return restaurant;
    }
}
