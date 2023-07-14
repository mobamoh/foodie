package com.mohamedbamoh.foodie.restaurant.container;

import com.mohamedbamoh.foodie.restaurant.core.domain.RestaurantDomainService;
import com.mohamedbamoh.foodie.restaurant.core.domain.RestaurantDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestaurantDomainService restaurantDomainService() {
        return new RestaurantDomainServiceImpl();
    }
}
