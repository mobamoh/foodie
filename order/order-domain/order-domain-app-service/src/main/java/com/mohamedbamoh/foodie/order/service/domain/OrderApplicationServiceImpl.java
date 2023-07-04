package com.mohamedbamoh.foodie.order.service.domain;

import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderResponse;
import com.mohamedbamoh.foodie.order.service.domain.dto.track.TrackOrderQuery;
import com.mohamedbamoh.foodie.order.service.domain.dto.track.TrackOrderResponse;
import com.mohamedbamoh.foodie.order.service.domain.port.input.service.OrderApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;

    private final OrderTrackCommandHandler orderTrackCommandHandler;

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
