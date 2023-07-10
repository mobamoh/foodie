package com.mohamedbamoh.foodie.payment.service.domain.port.output.repository;

import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditHistory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditHistoryRepository {

    CreditHistory save(CreditHistory creditHistory);

    Optional<List<CreditHistory>> findByCustomerId(UUID customerId);
}
