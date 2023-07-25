package com.mohamedbamoh.foodie.order.domain.app.service.outbox.scheduler.approval;

import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval.OrderApprovalOutboxMessage;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.restaurant.RestaurantApprovalRequestMessagePublisher;
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
public class RestaurantApprovalOutboxScheduler implements OutboxScheduler {

    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final RestaurantApprovalRequestMessagePublisher restaurantApprovalRequestMessagePublisher;

    @Override
    @Transactional
    @Scheduled(fixedRateString = "${order-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        var outboxMessagesResponse = approvalOutboxHelper.getByOutboxStatusAndSagaStatus(OutboxStatus.STARTED,
                SagaStatus.PROCESSING);
        if (outboxMessagesResponse.isPresent() && outboxMessagesResponse.get().size() > 0) {
            var outboxMessages = outboxMessagesResponse.get();
            log.info("Received {} OrderApprovalOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream().map(m -> m.getId().toString()).collect(Collectors.joining(",")));
            outboxMessages.forEach(m -> restaurantApprovalRequestMessagePublisher.publish(m, this::updateOutboxStatus));
            log.info("{} OrderApprovalOutboxMessage sent to bus!", outboxMessages.size());
        }
    }

    private void updateOutboxStatus(OrderApprovalOutboxMessage orderApprovalOutboxMessage, OutboxStatus outboxStatus) {
        orderApprovalOutboxMessage.setOutboxStatus(outboxStatus);
        approvalOutboxHelper.save(orderApprovalOutboxMessage);
        log.info("OrderApprovalOutboxMessage updated with outbox status: {}", outboxStatus.name());
    }
}
