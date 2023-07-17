package com.mohamedbamoh.foodie.payment.data.access.payment.adapter;

import com.mohamedbamoh.foodie.payment.domain.core.entity.Payment;
import com.mohamedbamoh.foodie.payment.data.access.payment.mapper.PaymentDataAccessMapper;
import com.mohamedbamoh.foodie.payment.data.access.payment.repository.PaymentJpaRepository;
import com.mohamedbamoh.foodie.payment.domain.app.service.port.output.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    @Override
    public Payment save(Payment payment) {
        return paymentDataAccessMapper.
                paymentEntityToPayment(
                        paymentJpaRepository.save(
                                paymentDataAccessMapper.
                                        paymentToPaymentEntity(payment)));
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(paymentDataAccessMapper::paymentEntityToPayment);
    }
}
