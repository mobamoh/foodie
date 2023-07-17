package com.mohamedbamoh.foodie.payment.domain.app.service.port.output.repository;

import com.mohamedbamoh.foodie.payment.domain.core.entity.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByOrderId(UUID orderId);
}
