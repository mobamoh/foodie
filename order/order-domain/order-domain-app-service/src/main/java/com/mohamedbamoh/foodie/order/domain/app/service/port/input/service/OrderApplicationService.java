package com.mohamedbamoh.foodie.order.domain.app.service.port.input.service;

import com.mohamedbamoh.foodie.order.domain.app.service.dto.track.TrackOrderQuery;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.track.TrackOrderResponse;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.domain.app.service.dto.create.CreateOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
