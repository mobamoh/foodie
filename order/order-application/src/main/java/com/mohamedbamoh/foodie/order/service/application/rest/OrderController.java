package com.mohamedbamoh.foodie.order.service.application.rest;

import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderResponse;
import com.mohamedbamoh.foodie.order.service.domain.dto.track.TrackOrderQuery;
import com.mohamedbamoh.foodie.order.service.domain.dto.track.TrackOrderResponse;
import com.mohamedbamoh.foodie.order.service.domain.port.input.service.OrderApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("Creating order for customer: {} at restaurant: {}",
                createOrderCommand.getCustomerId(), createOrderCommand.getRestaurantId());
        var createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order created with tracking id: {}", createOrderResponse.getTrackingId());
        return ResponseEntity.ok(createOrderResponse);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable UUID trackingId) {
        var trackOrderResponse = orderApplicationService.trackOrder(TrackOrderQuery.builder()
                .orderTrackingId(trackingId).build());
        log.info("Return order status with tracking id: {}", trackOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(trackOrderResponse);
    }

}
