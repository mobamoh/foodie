package com.mohamedbamoh.foodie.restaurant.domain.app.service.exception;

import com.mohamedbamoh.foodie.common.domain.exception.DomainException;

public class RestaurantApplicationServiceException extends DomainException {
    public RestaurantApplicationServiceException(String message) {
        super(message);
    }

    public RestaurantApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
