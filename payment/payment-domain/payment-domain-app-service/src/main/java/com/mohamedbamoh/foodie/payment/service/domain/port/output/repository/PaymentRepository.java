package com.mohamedbamoh.foodie.payment.service.domain.port.output.repository;

import com.mohamedbamoh.foodie.payment.core.domain.entity.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByOrderId(UUID orderId);
}
