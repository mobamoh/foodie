package com.mohamedbamoh.foodie.order.messaging.listener.kafka;

import com.mohamedbamoh.foodie.kafka.consumer.KafkaConsumer;
import com.mohamedbamoh.foodie.kafka.order.avro.model.OrderApprovalStatus;
import com.mohamedbamoh.foodie.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import com.mohamedbamoh.foodie.order.domain.app.service.port.input.message.listener.restaurant.RestaurantApprovalResponseMessageListener;
import com.mohamedbamoh.foodie.order.domain.core.exception.OrderNotFoundException;
import com.mohamedbamoh.foodie.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {

    private final RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
            topics = "${order-service.restaurant-approval-response-topic-name}")
    public void receive(@Payload List<RestaurantApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} number of restaurant approval responses received with keys: {}, partitions: {} and offsets: {}",
                messages.size(), keys.toString(), partitions.toString(), offsets.toString());

        messages.forEach(restaurantApprovalResponse -> {
            try {
                if (OrderApprovalStatus.APPROVED == restaurantApprovalResponse.getOrderApprovalStatus()) {
                    log.info("Processing approved order: {}", restaurantApprovalResponse.getOrderId());
                    restaurantApprovalResponseMessageListener.orderApproved(orderMessagingDataMapper.
                            approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponse)
                    );
                } else if (OrderApprovalStatus.REJECTED == restaurantApprovalResponse.getOrderApprovalStatus()) {
                    log.info("Processing rejected order: {}, with failure messages: {}",
                            restaurantApprovalResponse.getOrderId(), String.join(",", restaurantApprovalResponse.getFailureMessages()));
                    restaurantApprovalResponseMessageListener.orderRejected(orderMessagingDataMapper.
                            approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponse));
                }
            } catch (OptimisticLockingFailureException e) {
                //NO-OP for optimistic lock. This means another thread finished the work, do not throw error to prevent reading the data from kafka again!
                log.error("Caught optimistic locking exception in RestaurantApprovalResponseKafkaListener for order id: {}",
                        restaurantApprovalResponse.getOrderId());
            } catch (OrderNotFoundException e) {
                //NO-OP for OrderNotFoundException
                log.error("No order found for order id: {}", restaurantApprovalResponse.getOrderId());
            }
        });
    }
}

