package com.mohamedbamoh.foodie.restaurant.messaging.publisher.kafka;

import com.mohamedbamoh.foodie.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import com.mohamedbamoh.foodie.kafka.producer.KafkaMessageHelper;
import com.mohamedbamoh.foodie.kafka.producer.service.KafkaProducer;
import com.mohamedbamoh.foodie.restaurant.core.domain.event.OrderApprovedEvent;
import com.mohamedbamoh.foodie.restaurant.messaging.mapper.RestaurantMessagingDataMapper;
import com.mohamedbamoh.foodie.restaurant.service.app.domain.config.RestaurantServiceConfigData;
import com.mohamedbamoh.foodie.restaurant.service.app.domain.port.output.message.publisher.OrderApprovedMessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderApprovedKafkaMessagePublisher implements OrderApprovedMessagePublisher {

    private final RestaurantMessagingDataMapper restaurantMessagingDataMapper;
    private final KafkaProducer<String, RestaurantApprovalResponseAvroModel> kafkaProducer;
    private final RestaurantServiceConfigData restaurantServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    @Override
    public void publish(OrderApprovedEvent domainEvent) {
        var orderId = domainEvent.getOrderApproval().getOrderId().getValue().toString();
        log.info("Received OrderApprovedEvent for order: {}", orderId);
        try {
            var responseAvroModel = restaurantMessagingDataMapper.orderApprovedConverter(domainEvent);
            kafkaProducer.send(restaurantServiceConfigData.getRestaurantApprovalResponseTopicName(),
                    orderId,
                    responseAvroModel,
                    kafkaMessageHelper.getKafkaCallBack(restaurantServiceConfigData.getRestaurantApprovalResponseTopicName(),
                            responseAvroModel, orderId, "RestaurantApprovalResponseAvroModel"));

            log.info("RestaurantApprovalResponseAvroModel sent to kafka at: {}", System.nanoTime());
        } catch (Exception e) {
            log.error("Error while sending RestaurantApprovalResponseAvroModel message to kafka for order: {}, error: {}",
                    orderId, e.getMessage());
        }

    }
}
