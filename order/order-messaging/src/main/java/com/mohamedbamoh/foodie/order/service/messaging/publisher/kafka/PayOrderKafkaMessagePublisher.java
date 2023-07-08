package com.mohamedbamoh.foodie.order.service.messaging.publisher.kafka;

import com.mohamedbamoh.foodie.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.mohamedbamoh.foodie.kafka.producer.service.KafkaProducer;
import com.mohamedbamoh.foodie.order.core.domain.event.OrderPaidEvent;
import com.mohamedbamoh.foodie.order.service.domain.config.OrderServiceConfigData;
import com.mohamedbamoh.foodie.order.service.domain.port.output.message.publisher.restaurant.OrderPaidRestaurantRequestMessagePublisher;
import com.mohamedbamoh.foodie.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        var orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderPaidEvent for order: {}", orderId);
        try {
            var restaurantApprovalRequestAvroModel = orderMessagingDataMapper.orderPaidToRestaurantApprovalRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId, restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallBack(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                            restaurantApprovalRequestAvroModel, orderId, "RestaurantApprovalRequestAvroModel"));
            log.info("RestaurantApprovalRequestAvroModel sent to kafka for order: {}", restaurantApprovalRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error while sending RestaurantApprovalRequestAvroModel message to kafka for order: {} error: {}",
                    orderId, e.getMessage());
        }
    }
}
