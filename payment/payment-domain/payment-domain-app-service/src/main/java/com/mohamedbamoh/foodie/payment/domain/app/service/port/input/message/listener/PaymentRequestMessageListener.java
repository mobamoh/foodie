package com.mohamedbamoh.foodie.payment.domain.app.service.port.input.message.listener;

import com.mohamedbamoh.foodie.payment.domain.app.service.dto.PaymentRequest;

public interface PaymentRequestMessageListener {

    void completePayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);
}
