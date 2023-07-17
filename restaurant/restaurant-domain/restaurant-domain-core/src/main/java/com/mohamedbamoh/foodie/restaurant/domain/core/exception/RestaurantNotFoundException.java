package com.mohamedbamoh.foodie.restaurant.domain.core.exception;

import com.mohamedbamoh.foodie.common.domain.exception.DomainException;

public class RestaurantNotFoundException extends DomainException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
