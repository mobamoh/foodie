package com.mohamedbamoh.foodie.payment.core.domain.exception;

import com.mohamedbamoh.foodie.domain.exception.DomainException;

public class PaymentNotFoundException extends DomainException {
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
