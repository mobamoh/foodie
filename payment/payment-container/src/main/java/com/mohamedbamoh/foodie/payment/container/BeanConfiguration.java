package com.mohamedbamoh.foodie.payment.container;

import com.mohamedbamoh.foodie.payment.domain.core.PaymentDomainService;
import com.mohamedbamoh.foodie.payment.domain.core.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();
    }
}
