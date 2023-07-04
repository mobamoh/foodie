package com.mohamedbamoh.foodie.order.service.domain;

import com.mohamedbamoh.foodie.order.core.domain.event.OrderCreatedEvent;
import com.mohamedbamoh.foodie.order.service.domain.port.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionalEventListener;

// approach 2
@Slf4j
//@Component
@AllArgsConstructor
public class OrderCreatedEventApplicationListener2 {

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    @TransactionalEventListener
    void process(OrderCreatedEvent orderCreatedEvent) {
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    }
}
