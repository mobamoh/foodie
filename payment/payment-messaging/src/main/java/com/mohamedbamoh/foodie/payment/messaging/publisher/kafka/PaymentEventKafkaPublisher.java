package com.mohamedbamoh.foodie.payment.messaging.publisher.kafka;

import com.mohamedbamoh.foodie.kafka.order.avro.model.PaymentResponseAvroModel;
import com.mohamedbamoh.foodie.kafka.producer.KafkaMessageHelper;
import com.mohamedbamoh.foodie.kafka.producer.service.KafkaProducer;
import com.mohamedbamoh.foodie.outbox.OutboxStatus;
import com.mohamedbamoh.foodie.payment.domain.app.service.config.PaymentServiceConfigData;
import com.mohamedbamoh.foodie.payment.domain.app.service.outbox.model.OrderEventPayload;
import com.mohamedbamoh.foodie.payment.domain.app.service.outbox.model.OrderOutboxMessage;
import com.mohamedbamoh.foodie.payment.domain.app.service.port.output.message.publisher.PaymentResponseMessagePublisher;
import com.mohamedbamoh.foodie.payment.messaging.mapper.PaymentMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class PaymentEventKafkaPublisher implements PaymentResponseMessagePublisher {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PaymentEventKafkaPublisher(PaymentMessagingDataMapper paymentMessagingDataMapper,
                                      KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer,
                                      PaymentServiceConfigData paymentServiceConfigData,
                                      KafkaMessageHelper kafkaMessageHelper) {
        this.paymentMessagingDataMapper = paymentMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.paymentServiceConfigData = paymentServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(OrderOutboxMessage orderOutboxMessage,
                        BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback) {
        OrderEventPayload orderEventPayload =
                kafkaMessageHelper.getOrderEventPayload(orderOutboxMessage.getPayload(), OrderEventPayload.class);

        String sagaId = orderOutboxMessage.getSagaId().toString();

        log.info("Received OrderOutboxMessage for order id: {} and saga id: {}",
                orderEventPayload.getOrderId(),
                sagaId);

        try {
            PaymentResponseAvroModel paymentResponseAvroModel = paymentMessagingDataMapper
                    .orderEventPayloadToPaymentResponseAvroModel(sagaId, orderEventPayload);

            kafkaProducer.send(paymentServiceConfigData.getPaymentResponseTopicName(),
                    sagaId,
                    paymentResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            orderOutboxMessage,
                            outboxCallback,
                            orderEventPayload.getOrderId(),
                            "PaymentResponseAvroModel"));

            log.info("PaymentResponseAvroModel sent to kafka for order id: {} and saga id: {}",
                    paymentResponseAvroModel.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message" +
                            " to kafka with order id: {} and saga id: {}, error: {}",
                    orderEventPayload.getOrderId(), sagaId, e.getMessage());
        }
    }
}
