package com.mohamedbamoh.foodie.order.domain.app.service;

import com.mohamedbamoh.foodie.order.domain.core.exception.OrderNotFoundException;
import com.mohamedbamoh.foodie.order.domain.core.valueobject.TrackingId;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.track.TrackOrderQuery;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.track.TrackOrderResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.mapper.OrderDataMapper;
import com.mohamedbamoh.foodie.order.domain.app.service.port.output.repository.OrderRepository;
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
