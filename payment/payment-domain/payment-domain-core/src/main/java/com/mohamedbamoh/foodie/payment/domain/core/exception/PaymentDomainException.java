package com.mohamedbamoh.foodie.payment.domain.core.exception;

import com.mohamedbamoh.foodie.common.domain.exception.DomainException;

public class PaymentDomainException extends DomainException {
    public PaymentDomainException(String message) {
        super(message);
    }

    public PaymentDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
