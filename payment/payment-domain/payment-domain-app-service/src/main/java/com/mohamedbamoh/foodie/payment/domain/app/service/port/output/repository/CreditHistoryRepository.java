package com.mohamedbamoh.foodie.payment.domain.app.service.port.output.repository;

import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.payment.domain.core.entity.CreditHistory;

import java.util.List;
import java.util.Optional;

public interface CreditHistoryRepository {

    CreditHistory save(CreditHistory creditHistory);

    Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);
}
