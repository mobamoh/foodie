package com.mohamedbamoh.foodie.customer.domain.core.exception;

import com.mohamedbamoh.foodie.common.domain.exception.DomainException;

public class CustomerDomainException extends DomainException {
    public CustomerDomainException(String message) {
        super(message);
    }
}
