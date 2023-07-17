package com.mohamedbamoh.foodie.payment.domain.app.service.exception;

import com.mohamedbamoh.foodie.common.domain.exception.DomainException;

public class PaymentApplicationServiceException extends DomainException {
    public PaymentApplicationServiceException(String message) {
        super(message);
    }

    public PaymentApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
