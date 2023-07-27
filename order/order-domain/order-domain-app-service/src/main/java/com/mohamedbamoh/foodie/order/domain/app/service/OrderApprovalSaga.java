package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.common.domain.valueobject.OrderStatus;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.message.RestaurantApprovalResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.mapper.OrderDataMapper;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval.OrderApprovalOutboxMessage;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.payment.OrderPaymentOutboxMessage;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.scheduler.approval.ApprovalOutboxHelper;
import com.mohamedbamoh.foodie.order.domain.app.service.outbox.scheduler.payment.PaymentOutboxHelper;
import com.mohamedbamoh.foodie.order.domain.core.OrderDomainService;
import com.mohamedbamoh.foodie.order.domain.core.entity.Order;
import com.mohamedbamoh.foodie.order.domain.core.event.OrderCancelledEvent;
import com.mohamedbamoh.foodie.order.domain.core.exception.OrderDomainException;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;
import com.mohamedbamoh.foodie.saga.SagaStatus;
import com.mohamedbamoh.foodie.saga.SagaStep;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.mohamedbamoh.foodie.common.domain.DomainConstants.UTC;

@Slf4j
@Component
@AllArgsConstructor
//public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponse, EmptyEvent, OrderCancelledEvent> {
public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponse> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final PaymentOutboxHelper paymentOutboxHelper;
    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final OrderDataMapper orderDataMapper;
//    private final OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;

    @Override
    @Transactional
    public void process(RestaurantApprovalResponse restaurantApprovalResponse) {

        var orderApprovalOutboxMessageOptional = approvalOutboxHelper.
                getBySagaIdAndSagaStatus(UUID.fromString(restaurantApprovalResponse.getSagaId()), SagaStatus.PROCESSING);

        if (orderApprovalOutboxMessageOptional.isEmpty()) {
            log.info("An outbox message with saga id: {} is already processed!", restaurantApprovalResponse.getSagaId());
            return;
        }

        var approvalOutboxMessage = orderApprovalOutboxMessageOptional.get();
        Order order = approveOrder(restaurantApprovalResponse);

        var sagaStatus = orderSagaHelper.orderStatusToSagaStatus(order.getOrderStatus());
        approvalOutboxHelper.save(getUpdatedApprovalOutboxMessage(approvalOutboxMessage, order.getOrderStatus(), sagaStatus));
        paymentOutboxHelper.save(getUpdatedPaymentOutboxMessage(restaurantApprovalResponse.getSagaId(), order.getOrderStatus(), sagaStatus));

        log.info("Order: {} has been approved", order.getId().getValue());
//        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public void rollback(RestaurantApprovalResponse restaurantApprovalResponse) {

        var orderApprovalOutboxMessageOptional = approvalOutboxHelper.
                getBySagaIdAndSagaStatus(UUID.fromString(restaurantApprovalResponse.getSagaId()), SagaStatus.PROCESSING);

        if (orderApprovalOutboxMessageOptional.isEmpty()) {
            log.info("An outbox message with saga id: {} is already rolled back!", restaurantApprovalResponse.getSagaId());
            return;
        }
        var approvalOutboxMessage = orderApprovalOutboxMessageOptional.get();
        var orderCancelledEvent = rollbackOrder(restaurantApprovalResponse);

        var sagaStatus = orderSagaHelper.orderStatusToSagaStatus(orderCancelledEvent.getOrder().getOrderStatus());
        approvalOutboxHelper.save(getUpdatedApprovalOutboxMessage(approvalOutboxMessage, orderCancelledEvent.getOrder().getOrderStatus(), sagaStatus));
        paymentOutboxHelper.save(orderDataMapper.orderCancelledEventToOrderPaymentEventPayload(orderCancelledEvent),
                orderCancelledEvent.getOrder().getOrderStatus(),
                sagaStatus,
                OutboxStatus.STARTED,
                UUID.fromString(restaurantApprovalResponse.getSagaId()));

        log.info("Order: {} has been cancelled", orderCancelledEvent.getOrder().getId().getValue());
//        return orderCancelledEvent;
    }

    private Order approveOrder(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Approving order: {}", restaurantApprovalResponse.getOrderId());
        var order = orderSagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        orderDomainService.approveOrder(order);
        orderSagaHelper.saveOrder(order);
        return order;
    }

    private OrderCancelledEvent rollbackOrder(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Cancelling order: {}", restaurantApprovalResponse.getOrderId());
        var order = orderSagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        var orderCancelledEvent = orderDomainService.cancelOrderPayment(order, restaurantApprovalResponse.getFailureMessages());
        orderSagaHelper.saveOrder(order);
        return orderCancelledEvent;
    }

    private OrderApprovalOutboxMessage getUpdatedApprovalOutboxMessage(OrderApprovalOutboxMessage approvalOutboxMessage,
                                                                       OrderStatus orderStatus, SagaStatus sagaStatus) {
        approvalOutboxMessage.setProcessedAt(ZonedDateTime.now(ZoneId.of(UTC)));
        approvalOutboxMessage.setOrderStatus(orderStatus);
        approvalOutboxMessage.setSagaStatus(sagaStatus);
        return approvalOutboxMessage;
    }

    private OrderPaymentOutboxMessage getUpdatedPaymentOutboxMessage(String sagaId, OrderStatus orderStatus,
                                                                     SagaStatus sagaStatus) {
        var paymentOutboxMessageOptional = paymentOutboxHelper.getBySagaIdAndSagaStatus(UUID.fromString(sagaId), SagaStatus.PROCESSING);
        if (paymentOutboxMessageOptional.isEmpty()) {
            throw new OrderDomainException(String.format("Payment outbox message cannot be found in %s state",
                    SagaStatus.PROCESSING.name()));
        }
        var orderPaymentOutboxMessage = paymentOutboxMessageOptional.get();
        orderPaymentOutboxMessage.setProcessedAt(ZonedDateTime.now(ZoneId.of(UTC)));
        orderPaymentOutboxMessage.setOrderStatus(orderStatus);
        orderPaymentOutboxMessage.setSagaStatus(sagaStatus);
        return orderPaymentOutboxMessage;
    }

}
