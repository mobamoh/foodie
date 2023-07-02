package com.mohamedbamoh.foodie.order.core.domain.exception;

import com.mohamedbamoh.foodie.domain.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
