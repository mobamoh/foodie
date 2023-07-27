package com.mohamedbamoh.foodie.order.data.access.outbox.restaurant.exception;

public class ApprovalOutboxNotFoundException extends RuntimeException {

    public ApprovalOutboxNotFoundException(String message) {
        super(message);
    }
}
