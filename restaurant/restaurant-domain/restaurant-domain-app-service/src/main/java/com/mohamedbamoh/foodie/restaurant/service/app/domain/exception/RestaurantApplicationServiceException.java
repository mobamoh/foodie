package com.mohamedbamoh.foodie.restaurant.service.app.domain.exception;

import com.mohamedbamoh.foodie.domain.exception.DomainException;

public class RestaurantApplicationServiceException extends DomainException {
    public RestaurantApplicationServiceException(String message) {
        super(message);
    }

    public RestaurantApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
