package com.mohamedbamoh.foodie.payment.domain.app.service;

import com.mohamedbamoh.foodie.payment.domain.app.service.dto.PaymentRequest;
import com.mohamedbamoh.foodie.payment.domain.app.service.port.input.message.listener.PaymentRequestMessageListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        paymentRequestHelper.persistPayment(paymentRequest);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        paymentRequestHelper.persistCancelPayment(paymentRequest);

    }
}
