package com.mohamedbamoh.foodie.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaMessageHelper {

    public <T> ListenableFutureCallback<SendResult<String, T>>
    getKafkaCallBack(String paymentRequestTopicName, T requestAvroModel, String orderId, String requestAvroModelName) {
        return new ListenableFutureCallback<SendResult<String, T>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while sending {} message: {} to topic: {}", requestAvroModelName,
                        requestAvroModel.toString(), paymentRequestTopicName, ex);
            }

            @Override
            public void onSuccess(SendResult<String, T> result) {
                var metadata = result.getRecordMetadata();
                log.info("Received successful response from Kafka for order: {} topic: {} partition: {} offset: {} timestamp: {}",
                        orderId,
                        metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
            }
        };
    }
}
