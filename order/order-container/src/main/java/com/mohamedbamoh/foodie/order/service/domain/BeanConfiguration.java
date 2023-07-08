package com.mohamedbamoh.foodie.order.service.domain;

import com.mohamedbamoh.foodie.order.core.domain.OrderDomainService;
import com.mohamedbamoh.foodie.order.core.domain.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
