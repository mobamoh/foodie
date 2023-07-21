package com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository;

import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval.OrderApprovalOutboxMessage;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;
import com.mohamedbamoh.foodie.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApprovalOutboxRepository {
    OrderApprovalOutboxMessage save(OrderApprovalOutboxMessage orderApprovalOutboxMessage);

    Optional<List<OrderApprovalOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    );

    Optional<List<OrderApprovalOutboxMessage>> findByTypeAndSagaIdAndSagaStatus(
            String type,
            UUID sagaId,
            SagaStatus... sagaStatus
    );

    void deleteByTypeAndOutboxStatusAndSagaStatus(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    );
}
