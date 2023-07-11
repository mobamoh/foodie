package com.mohamedbamoh.foodie.payment.service.domain;

import com.mohamedbamoh.foodie.payment.core.domain.PaymentDomainService;
import com.mohamedbamoh.foodie.payment.core.domain.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();
    }
}
