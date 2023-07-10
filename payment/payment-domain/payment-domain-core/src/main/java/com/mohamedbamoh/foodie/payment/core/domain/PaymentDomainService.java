package com.mohamedbamoh.foodie.payment.core.domain;

import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditEntry;
import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditHistory;
import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages);
}
