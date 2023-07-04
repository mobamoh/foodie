package com.mohamedbamoh.foodie.order.service.domain;

import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderResponse;
import com.mohamedbamoh.foodie.order.service.domain.mapper.OrderDataMapper;
import com.mohamedbamoh.foodie.order.service.domain.port.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderCreateCommandHandler {

    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        var orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),"Order created successfully");
    }


}
