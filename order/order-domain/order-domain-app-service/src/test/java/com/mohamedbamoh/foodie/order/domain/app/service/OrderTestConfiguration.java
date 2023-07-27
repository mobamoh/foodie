package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.restaurant.RestaurantApprovalRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.*;
import com.mohamedbamoh.foodie.order.domain.core.OrderDomainService;
import com.mohamedbamoh.foodie.order.domain.core.OrderDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.mohamedbamoh.foodie")
public class OrderTestConfiguration {

//    @Bean
//    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
//        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
//    }
//
//    @Bean
//    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
//        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
//    }
//
//    @Bean
//    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
//        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
//    }

    @Bean
    public PaymentRequestMessagePublisher paymentRequestMessagePublisher() {
        return Mockito.mock(PaymentRequestMessagePublisher.class);
    }

    @Bean
    public RestaurantApprovalRequestMessagePublisher restaurantApprovalRequestMessagePublisher() {
        return Mockito.mock(RestaurantApprovalRequestMessagePublisher.class);
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
    public PaymentOutboxRepository paymentOutboxRepository() {
        return Mockito.mock(PaymentOutboxRepository.class);
    }

    @Bean
    public ApprovalOutboxRepository approvalOutboxRepository() {
        return Mockito.mock(ApprovalOutboxRepository.class);
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
