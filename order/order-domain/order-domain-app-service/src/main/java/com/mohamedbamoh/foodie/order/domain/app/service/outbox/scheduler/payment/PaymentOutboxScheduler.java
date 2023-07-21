package com.mohamedbamoh.foodie.order.domain.app.service.outbox.scheduler.payment;

import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.payment.OrderPaymentOutboxMessage;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.mohamedbamoh.foodie.outbox.OutboxScheduler;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;
import com.mohamedbamoh.foodie.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentOutboxScheduler implements OutboxScheduler {

    private final PaymentOutboxHelper paymentOutboxHelper;
    private final PaymentRequestMessagePublisher paymentRequestMessagePublisher;

    @Override
    @Transactional
    @Scheduled(fixedRateString = "${order-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        var outboxMessagesResponse = paymentOutboxHelper.getByOutboxStatusAndSagaStatus(OutboxStatus.STARTED,
                SagaStatus.STARTED, SagaStatus.COMPENSATING);
        if (outboxMessagesResponse.isPresent() && outboxMessagesResponse.get().size() > 0) {
            var outboxMessages = outboxMessagesResponse.get();
            log.info("Received {} OrderPaymentOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream().map(m -> m.getId().toString()).collect(Collectors.joining(",")));
            outboxMessages.forEach(m -> paymentRequestMessagePublisher.publish(m, this::updateOutboxStatus));
            log.info("{} OrderPaymentOutboxMessage sent to bus!", outboxMessages.size());
        }
    }

    private void updateOutboxStatus(OrderPaymentOutboxMessage orderPaymentOutboxMessage, OutboxStatus outboxStatus) {
        orderPaymentOutboxMessage.setOutboxStatus(outboxStatus);
        paymentOutboxHelper.save(orderPaymentOutboxMessage);
        log.info("OrderPaymentOutboxMessage updated with outbox status: {}", outboxStatus.name());
    }
}
