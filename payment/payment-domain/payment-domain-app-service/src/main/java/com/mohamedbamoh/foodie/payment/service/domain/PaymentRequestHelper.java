package com.mohamedbamoh.foodie.payment.service.domain;

import com.mohamedbamoh.foodie.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.payment.core.domain.PaymentDomainService;
import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditEntry;
import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditHistory;
import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentEvent;
import com.mohamedbamoh.foodie.payment.service.domain.dto.PaymentRequest;
import com.mohamedbamoh.foodie.payment.service.domain.exception.PaymentApplicationServiceException;
import com.mohamedbamoh.foodie.payment.service.domain.mapper.PaymentDataMapper;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.message.publisher.PaymentCancelledMessagePublisher;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.message.publisher.PaymentCompletedMessagePublisher;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.message.publisher.PaymentFailedMessagePublisher;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.repository.CreditEntryRepository;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.repository.CreditHistoryRepository;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentRequestHelper {

    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;
    private final PaymentCompletedMessagePublisher paymentCompletedEventDomainEventPublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledEventDomainEventPublisher;
    private final PaymentFailedMessagePublisher paymentFailedEventDomainEventPublisher;

    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest) {
        log.info("Received payment complete event for order {}", paymentRequest.getOrderId());
        var payment = paymentDataMapper.paymentRequestToPayment(paymentRequest);

        var creditEntry = getCreditEntry(payment.getCustomerId());
        var creditHistories = getCreditHistory(payment.getCustomerId());
        var failureMessages = new ArrayList<String>();
        var paymentEvent = paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages,
                paymentCompletedEventDomainEventPublisher, paymentFailedEventDomainEventPublisher);
        persistDbObjects(payment, creditEntry, creditHistories, failureMessages);
        return paymentEvent;
    }

    @Transactional
    public PaymentEvent persistCancelPayment(PaymentRequest paymentRequest) {
        log.info("Received payment rollback event for order {}", paymentRequest.getOrderId());
        var optPayment = paymentRepository.findByOrderId(UUID.fromString(paymentRequest.getOrderId()));
        if (optPayment.isEmpty()) {
            log.error("Payment for order {} couldn't be found!", paymentRequest.getOrderId());
            throw new PaymentApplicationServiceException(
                    String.format("Payment for order %s couldn't be found!", paymentRequest.getOrderId())
            );
        }
        var payment = optPayment.get();
        //TODO : refactor to method
        var creditEntry = getCreditEntry(payment.getCustomerId());
        var creditHistories = getCreditHistory(payment.getCustomerId());
        var failureMessages = new ArrayList<String>();
        var paymentEvent = paymentDomainService.validateAndCancelPayment(payment, creditEntry, creditHistories, failureMessages,
                paymentCancelledEventDomainEventPublisher, paymentFailedEventDomainEventPublisher);
        persistDbObjects(payment, creditEntry, creditHistories, failureMessages);
        return paymentEvent;
    }

    private CreditEntry getCreditEntry(CustomerId customerId) {
        var optCustomer = creditEntryRepository.findByCustomerId(customerId);
        if (optCustomer.isEmpty()) {
            log.error("Couldn't find credit entry for customer {}", customerId.getValue());
            throw new PaymentApplicationServiceException(
                    String.format("Couldn't find credit entry for customer %s", customerId.getValue())
            );
        }
        return optCustomer.get();
    }

    private List<CreditHistory> getCreditHistory(CustomerId customerId) {
        Optional<List<CreditHistory>> optCreditHistory = creditHistoryRepository.findByCustomerId(customerId);
        if (optCreditHistory.isEmpty()) {
            log.error("Couldn't find credit history for customer {}", customerId.getValue());
            throw new PaymentApplicationServiceException(
                    String.format("Couldn't find credit history for customer %s", customerId.getValue())
            );
        }
        return optCreditHistory.get();
    }

    private void persistDbObjects(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, ArrayList<String> failureMessages) {
        paymentRepository.save(payment);
        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size() - 1));
        }
    }
}
