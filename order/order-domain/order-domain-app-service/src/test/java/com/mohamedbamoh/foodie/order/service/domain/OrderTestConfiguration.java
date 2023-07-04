package com.mohamedbamoh.foodie.order.service.domain;

import com.mohamedbamoh.foodie.order.core.domain.OrderDomainService;
import com.mohamedbamoh.foodie.order.core.domain.OrderDomainServiceImpl;
import com.mohamedbamoh.foodie.order.service.domain.port.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.service.domain.port.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.service.domain.port.output.message.publisher.restaurant.OrderPaidRestaurantRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.CustomerRepository;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.OrderRepository;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.mohamedbamoh.foodie")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }
}
