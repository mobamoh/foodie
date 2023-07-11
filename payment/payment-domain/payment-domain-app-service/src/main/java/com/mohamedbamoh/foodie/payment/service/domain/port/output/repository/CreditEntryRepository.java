package com.mohamedbamoh.foodie.payment.service.domain.port.output.repository;

import com.mohamedbamoh.foodie.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
