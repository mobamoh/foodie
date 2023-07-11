package com.mohamedbamoh.foodie.payment.service.messaging.publisher.kafka;

import com.mohamedbamoh.foodie.kafka.order.avro.model.PaymentResponseAvroModel;
import com.mohamedbamoh.foodie.kafka.producer.KafkaMessageHelper;
import com.mohamedbamoh.foodie.kafka.producer.service.KafkaProducer;
import com.mohamedbamoh.foodie.payment.core.domain.event.PaymentCancelledEvent;
import com.mohamedbamoh.foodie.payment.service.domain.config.PaymentServiceConfigData;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.message.publisher.PaymentCancelledMessagePublisher;
import com.mohamedbamoh.foodie.payment.service.messaging.mapper.PaymentMessagingDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PaymentCancelledKafkaMessagePublisher implements PaymentCancelledMessagePublisher {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    @Override
    public void publish(PaymentCancelledEvent domainEvent) {
        var orderId = domainEvent.getPayment().getOrderId().getValue().toString();
        log.info("Received PaymentCancelledEvent for order {}", orderId);
        try {
            var paymentResponseAvroModel = paymentMessagingDataMapper.paymentCancelledEventToPaymentResponseAvroModel(domainEvent);
            kafkaProducer.send(paymentServiceConfigData.getPaymentResponseTopicName(),
                    orderId,
                    paymentResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallBack(paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel, orderId, "PaymentResponseAvroModel"));
            log.info("PaymentResponseAvroModel sent to kafka for order {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending PaymentResponseAvroModel to kafka for order {}, error {}", orderId, e.getMessage());
        }
    }
}
