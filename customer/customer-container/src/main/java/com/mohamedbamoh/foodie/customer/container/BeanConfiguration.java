package com.mohamedbamoh.foodie.customer.container;

import com.mohamedbamoh.foodie.customer.domain.core.CustomerDomainService;
import com.mohamedbamoh.foodie.customer.domain.core.CustomerDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }
}
