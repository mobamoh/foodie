package com.mohamedbamoh.foodie.order.service.messaging.publisher.kafka;

import com.mohamedbamoh.foodie.kafka.order.avro.model.PaymentRequestAvroModel;
import com.mohamedbamoh.foodie.kafka.producer.service.KafkaProducer;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderCancalledEvent;
import com.mohamedbamoh.foodie.order.service.domain.config.OrderServiceConfigData;
import com.mohamedbamoh.foodie.order.service.domain.port.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CancelOrderKafkaMessagePublisher implements OrderCancelledPaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    @Override
    public void publish(OrderCancalledEvent domainEvent) {
        var orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderCancalledEvent for order: {}", orderId);
        try {
            var paymentRequestAvroModel = orderMessagingDataMapper.orderCancelledEventToPaymentRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallBack(orderServiceConfigData.getPaymentRequestTopicName(), paymentRequestAvroModel,
                            orderId, "PaymentRequestAvroModel"));
            log.info("PaymentRequestAvroModel sent to kafka for order: {}", paymentRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message to kafka for order: {} error: {}",
                    orderId, e.getMessage());
        }

    }


}
