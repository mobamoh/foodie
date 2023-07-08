package com.mohamedbamoh.foodie.order.service.messaging.listener.kafka;

import com.mohamedbamoh.foodie.kafka.consumer.KafkaConsumer;
import com.mohamedbamoh.foodie.kafka.order.avro.model.PaymentResponseAvroModel;
import com.mohamedbamoh.foodie.kafka.order.avro.model.PaymentStatus;
import com.mohamedbamoh.foodie.order.service.domain.port.input.message.listener.payment.PaymentResponseMessageListener;
import com.mohamedbamoh.foodie.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {

    private final PaymentResponseMessageListener paymentResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${order-service.payment-response-topic-name}")
    public void receive(@Payload List<PaymentResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of payment responses received with keys: {}, partitions: {} and offsets: {}",
                messages.size(), keys.toString(), partitions.toString(), offsets.toString());

        messages.forEach(paymentResponse -> {
            if (PaymentStatus.COMPLETED == paymentResponse.getPaymentStatus()) {
                log.info("Processing successful payment for order: {}", paymentResponse.getOrderId());
                paymentResponseMessageListener.paymentCompleted(orderMessagingDataMapper.
                        paymentResponseAvroModelToPaymentResponse(paymentResponse));
            } else if (PaymentStatus.CANCELLED == paymentResponse.getPaymentStatus() ||
                    PaymentStatus.FAILED == paymentResponse.getPaymentStatus()) {
                log.info("Processing failed payment for order: {}", paymentResponse.getOrderId());
                paymentResponseMessageListener.paymentCancelled(orderMessagingDataMapper.
                        paymentResponseAvroModelToPaymentResponse(paymentResponse));
            }
        });
    }
}
