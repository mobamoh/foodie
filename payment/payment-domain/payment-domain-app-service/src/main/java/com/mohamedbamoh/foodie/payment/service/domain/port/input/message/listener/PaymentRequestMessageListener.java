package com.mohamedbamoh.foodie.payment.service.domain.port.input.message.listener;

import com.mohamedbamoh.foodie.payment.service.domain.dto.PaymentRequest;

public interface PaymentRequestMessageListener {

    void completePayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);
}
