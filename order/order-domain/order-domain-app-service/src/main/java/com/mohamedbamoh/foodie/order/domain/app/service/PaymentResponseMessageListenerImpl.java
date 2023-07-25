package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.PaymentResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.port.input.message.listener.payment.PaymentResponseMessageListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    private final OrderPaymentSaga orderPaymentSaga;

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        orderPaymentSaga.process(paymentResponse);
        log.info("Order payment saga process operation is competed for order: {}", paymentResponse.getOrderId());
//        log.info("Publishing OrderPaidEvent for order: {}", paymentResponse.getOrderId());
//        orderPaidEvent.fire();

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info("Rollback order: {} with failure messages: {}",
                paymentResponse.getPaymentId(),
                String.join(",", paymentResponse.getFailureMessages()));

    }
}
