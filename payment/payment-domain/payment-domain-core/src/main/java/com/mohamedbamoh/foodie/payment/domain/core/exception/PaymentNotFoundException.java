package com.mohamedbamoh.foodie.payment.domain.core.exception;

import com.mohamedbamoh.foodie.common.domain.exception.DomainException;

public class PaymentNotFoundException extends DomainException {
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
