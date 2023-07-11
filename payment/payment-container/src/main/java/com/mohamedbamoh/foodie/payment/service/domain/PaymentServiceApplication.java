package com.mohamedbamoh.foodie.payment.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.mohamedbamoh.foodie.payment.data.access")
@EntityScan(basePackages = "com.mohamedbamoh.foodie.payment.data.access")
@SpringBootApplication(scanBasePackages = "com.mohamedbamoh.foodie")
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
