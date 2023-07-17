package com.mohamedbamoh.foodie.order.domain.app.service.port.input.message.listener.payment;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
