package com.mohamedbamoh.foodie.order.service.domain.port.input.message.listener.payment;

import com.mohamedbamoh.foodie.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
