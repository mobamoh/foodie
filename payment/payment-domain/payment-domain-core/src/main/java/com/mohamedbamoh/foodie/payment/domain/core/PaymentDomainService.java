package com.mohamedbamoh.foodie.payment.domain.core;

import com.mohamedbamoh.foodie.common.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.payment.domain.core.entity.CreditEntry;
import com.mohamedbamoh.foodie.payment.domain.core.entity.CreditHistory;
import com.mohamedbamoh.foodie.payment.domain.core.entity.Payment;
import com.mohamedbamoh.foodie.payment.domain.core.event.PaymentCancelledEvent;
import com.mohamedbamoh.foodie.payment.domain.core.event.PaymentCompletedEvent;
import com.mohamedbamoh.foodie.payment.domain.core.event.PaymentEvent;
import com.mohamedbamoh.foodie.payment.domain.core.event.PaymentFailedEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages,
                                            DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher,
                                            DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages,
                                          DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher,
                                          DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);
}
