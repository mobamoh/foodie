package com.mohamedbamoh.foodie.payment.domain.core;

import com.mohamedbamoh.foodie.common.domain.event.publisher.DomainEventPublisher;
import com.mohamedbamoh.foodie.common.domain.valueobject.Money;
import com.mohamedbamoh.foodie.common.domain.valueobject.PaymentStatus;
import com.mohamedbamoh.foodie.payment.domain.core.entity.CreditEntry;
import com.mohamedbamoh.foodie.payment.domain.core.entity.CreditHistory;
import com.mohamedbamoh.foodie.payment.domain.core.entity.Payment;
import com.mohamedbamoh.foodie.payment.domain.core.event.PaymentCancelledEvent;
import com.mohamedbamoh.foodie.payment.domain.core.event.PaymentCompletedEvent;
import com.mohamedbamoh.foodie.payment.domain.core.event.PaymentEvent;
import com.mohamedbamoh.foodie.payment.domain.core.event.PaymentFailedEvent;
import com.mohamedbamoh.foodie.payment.domain.core.valueobject.CreditHistoryId;
import com.mohamedbamoh.foodie.payment.domain.core.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.mohamedbamoh.foodie.common.domain.DomainConstants.UTC;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {
    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages, DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
        validateCreditHistory(creditEntry, creditHistories, failureMessages);

        if (failureMessages.isEmpty()) {
            log.info("Payment initiated for order {}", payment.getOrderId());
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentCompletedEventDomainEventPublisher);
        }

        log.info("Payment initiation failed for order {}", payment.getOrderId());
        payment.updateStatus(PaymentStatus.FAILED);
        return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages, paymentFailedEventDomainEventPublisher);
    }

    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages, DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher) {
        payment.validatePayment(failureMessages);
        addCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);

        if (failureMessages.isEmpty()) {
            log.info("Payment is cancelled for order {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.CANCELLED);
            return new PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentCancelledEventDomainEventPublisher);
        }

        log.info("Payment cancellation is failed for order {}", payment.getOrderId().getValue());
        payment.updateStatus(PaymentStatus.FAILED);
        return new PaymentFailedEvent(payment,ZonedDateTime.now(ZoneId.of(UTC)),failureMessages, paymentFailedEventDomainEventPublisher);
    }

    private void addCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.addCreditAmount(payment.getPrice());
    }

    private void validateCreditEntry(Payment payment, CreditEntry creditEntry, List<String> failureMessages) {
        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error("Customer {} doesn't have enough credit for payment!",
                    payment.getCustomerId().getValue());

            failureMessages.add(String.format("Customer %s doesn't have enough credit for payment!",
                    payment.getCustomerId().getValue()));
        }
    }

    private void subtractCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAmount(payment.getPrice());
    }

    private void updateCreditHistory(Payment payment, List<CreditHistory> creditHistories, TransactionType transactionType) {
        creditHistories.add(CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
                .transactionType(transactionType)
                .amount(payment.getPrice())
                .customerId(payment.getCustomerId())
                .build());
    }

    private void validateCreditHistory(CreditEntry creditEntry,
                                       List<CreditHistory> creditHistories, List<String> failureMessages) {

        var totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
        var totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);
        if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
            log.error("Customer {} doesn't have enough credit according to credit history!",
                    creditEntry.getCustomerId().getValue());
            failureMessages.add(String.format("Customer %s doesn't have enough credit according to credit history!",
                    creditEntry.getCustomerId().getValue()));
        }
        if (!creditEntry.getTotalCreditAmount().equals(totalCreditHistory.subtract(totalDebitHistory))) {
            log.error("Credit history total is not equal to current credit for customer {}!",
                    creditEntry.getCustomerId().getValue());
            failureMessages.add(String.format("Credit history total is not equal to current credit for customer %s!",
                    creditEntry.getCustomerId().getValue()));
        }
    }

    private Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream()
                .filter(ch -> transactionType == ch.getTransactionType())
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }
}
