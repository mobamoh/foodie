package com.mohamedbamoh.foodie.payment.service.domain;

import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentEvent;
import com.mohamedbamoh.foodie.payment.service.domain.dto.PaymentRequest;
import com.mohamedbamoh.foodie.payment.service.domain.port.input.message.listener.PaymentRequestMessageListener;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.message.publisher.PaymentCancelledMessagePublisher;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.message.publisher.PaymentCompletedMessagePublisher;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.message.publisher.PaymentFailedMessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;
    private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;
    private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        var paymentEvent = paymentRequestHelper.persistPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        var paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest);
        fireEvent(paymentEvent);

    }

    private void fireEvent(PaymentEvent paymentEvent) {
    }
}
