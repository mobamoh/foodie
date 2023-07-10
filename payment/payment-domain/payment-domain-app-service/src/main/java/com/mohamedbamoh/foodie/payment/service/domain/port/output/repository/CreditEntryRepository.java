package com.mohamedbamoh.foodie.payment.service.domain.port.output.repository;

import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditEntry;

import java.util.Optional;
import java.util.UUID;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(UUID customerId);
}
