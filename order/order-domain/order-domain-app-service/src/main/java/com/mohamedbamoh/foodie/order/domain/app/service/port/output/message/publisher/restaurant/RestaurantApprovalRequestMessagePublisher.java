package com.mohamedbamoh.foodie.order.domain.app.service.port.output.message.publisher.restaurant;

import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval.OrderApprovalOutboxMessage;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface RestaurantApprovalRequestMessagePublisher {
    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                 BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
