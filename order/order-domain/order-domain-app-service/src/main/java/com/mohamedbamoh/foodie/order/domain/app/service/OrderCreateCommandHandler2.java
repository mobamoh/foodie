package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.CreateOrderResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.mapper.OrderDataMapper;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.CustomerRepository;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.OrderRepository;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.RestaurantRepository;
import com.mohamedbamoh.foodie.order.domain.core.OrderDomainService;
import com.mohamedbamoh.foodie.order.domain.core.entity.Customer;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import com.mohamedbamoh.foodie.order.domain.core.entity.Restaurant;
import com.mohamedbamoh.foodie.order.domain.core.exception.OrderDomainException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

// approach 2
@Slf4j
@AllArgsConstructor
public class OrderCreateCommandHandler2 {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;
    private final ApplicationDomainEventPublisher2 applicationDomainEventPublisher;
//    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedEventDomainEventPublisher;

    //    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        var restaurant = checkRestaurant(createOrderCommand);
        var order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        var orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        var createdOrder = saveOrder(order);
        log.info("Order with id: {} created successfully!", createdOrder.getId());
        applicationDomainEventPublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(createdOrder, "");
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        var restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Could not find restaurant with id: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with id:" + createOrderCommand.getRestaurantId());
        }
        return optionalRestaurant.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with id: {}", customerId);
            throw new OrderDomainException("Could not find customer with id: " + customerId);
        }
    }

    private Order saveOrder(Order order) {
        var savedOrder = orderRepository.save(order);
        if (savedOrder == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order saved!");
        return savedOrder;
    }
}
