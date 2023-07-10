package com.mohamedbamoh.foodie.payment.core.domain;

import com.mohamedbamoh.foodie.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditEntry;
import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditHistory;
import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentCancelledEvent;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentCompletedEvent;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentEvent;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentFailedEvent;

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
