package com.mohamedbamoh.foodie.kafka.producer.service.impl;

import com.mohamedbamoh.foodie.kafka.producer.exception.KafkaProducerException;
import com.mohamedbamoh.foodie.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private KafkaTemplate<K, V> kafkaTemplate;

    @Override
    public void send(String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            CompletableFuture<SendResult<K, V>> resultFuture = kafkaTemplate.send(topicName, key, message);
            resultFuture.whenComplete(callback);
        } catch (KafkaException e) {
            log.error("Error on kafka producer with key: {}, message: {} and excption: {} ",
                    key, message, e.getMessage());
            throw new KafkaProducerException(String.format("Error on kafka producer with key: %s, message: %s",
                    key, message));
        }

    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
