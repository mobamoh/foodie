package com.mohamedbamoh.foodie.order.service.domain;

import com.mohamedbamoh.foodie.order.core.domain.exception.OrderNotFoundException;
import com.mohamedbamoh.foodie.order.core.domain.valueobject.TrackingId;
import com.mohamedbamoh.foodie.order.service.domain.dto.track.TrackOrderQuery;
import com.mohamedbamoh.foodie.order.service.domain.dto.track.TrackOrderResponse;
import com.mohamedbamoh.foodie.order.service.domain.mapper.OrderDataMapper;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        var orderOptional = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (orderOptional.isEmpty()) {
            log.warn("Order with tracking id: {} not found!", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException(String.format("Order with tracking id: %s not found!",
                    trackOrderQuery.getOrderTrackingId()));
        }
        return orderDataMapper.orderToTrackOrderResponse(orderOptional.get());
    }
}
