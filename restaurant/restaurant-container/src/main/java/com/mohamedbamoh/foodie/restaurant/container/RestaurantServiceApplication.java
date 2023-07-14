package com.mohamedbamoh.foodie.restaurant.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.mohamedbamoh.foodie.restaurant.data.access",
        "com.mohamedbamoh.foodie.common.data.access"})
@EntityScan(basePackages = {"com.mohamedbamoh.foodie.restaurant.data.access",
        "com.mohamedbamoh.foodie.common.data.access"})
@SpringBootApplication(scanBasePackages = "com.mohamedbamoh.foodie")
public class RestaurantServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }
}
