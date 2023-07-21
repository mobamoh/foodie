package com.mohamedbamoh.foodie.order.domain.app.service.outbox.scheduler.payment;

import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.payment.OrderPaymentOutboxMessage;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.PaymentOutboxRepository;
import com.mohamedbamoh.foodie.order.domain.core.exception.OrderDomainException;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;
import com.mohamedbamoh.foodie.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mohamedbamoh.foodie.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentOutboxHelper {
    private final PaymentOutboxRepository paymentOutboxRepository;

    @Transactional(readOnly = true)
    public Optional<List<OrderPaymentOutboxMessage>> getByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME, outboxStatus, sagaStatus);
    }

    @Transactional(readOnly = true)
    public Optional<OrderPaymentOutboxMessage> getBySagaIdAndSagaStatus(UUID sagaId, SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndSagaIdAndSagaStatus(ORDER_SAGA_NAME, sagaId, sagaStatus);
    }

    @Transactional
    void save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
        var reponse = paymentOutboxRepository.save(orderPaymentOutboxMessage);
        if (reponse == null) {
            log.error("Could not save OrderPaymentOutboxMessage with outbox id: {}", orderPaymentOutboxMessage.getId());
            throw new OrderDomainException(String.format("Could not save OrderPaymentOutboxMessage with outbox id: %s",
                    orderPaymentOutboxMessage.getId()));
        }
        log.info("OrderPaymentOutboxMessage saved with outbox id: {}", orderPaymentOutboxMessage.getId());
    }
}
