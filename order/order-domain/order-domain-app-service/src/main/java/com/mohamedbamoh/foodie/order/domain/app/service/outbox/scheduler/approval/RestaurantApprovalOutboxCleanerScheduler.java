package com.mohamedbamoh.foodie.order.domain.app.service.outbox.scheduler.approval;

import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval.OrderApprovalOutboxMessage;
import com.mohamedbamoh.foodie.outbox.OutboxScheduler;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;
import com.mohamedbamoh.foodie.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class RestaurantApprovalOutboxCleanerScheduler implements OutboxScheduler {

    private final ApprovalOutboxHelper approvalOutboxHelper;

    @Override
    public void processOutboxMessage() {
        var outboxResponseList = approvalOutboxHelper.getByOutboxStatusAndSagaStatus(OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED, SagaStatus.FAILED, SagaStatus.COMPENSATED);
        if (outboxResponseList.isPresent()) {
            var results = outboxResponseList.get();
            log.info("Received {} OrderApprovalOutboxMessage for clean-up. The payload: {}",
                    results.size(), results.stream()
                            .map(OrderApprovalOutboxMessage::getPayload)
                            .collect(Collectors.joining("\n")));
            approvalOutboxHelper.deleteByOutboxStatusAndSagaStatus(OutboxStatus.COMPLETED,
                    SagaStatus.SUCCEEDED, SagaStatus.FAILED, SagaStatus.COMPENSATED);
            log.info("OrderApprovalOutboxMessage {} deleted!", results.size());
        }
    }
}
