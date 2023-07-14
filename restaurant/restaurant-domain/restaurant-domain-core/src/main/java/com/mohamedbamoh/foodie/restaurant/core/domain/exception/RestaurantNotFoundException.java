package com.mohamedbamoh.foodie.restaurant.core.domain.exception;

import com.mohamedbamoh.foodie.domain.exception.DomainException;

public class RestaurantNotFoundException extends DomainException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
