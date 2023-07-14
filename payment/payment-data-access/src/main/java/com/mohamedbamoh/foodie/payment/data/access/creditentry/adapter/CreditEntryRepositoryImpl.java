package com.mohamedbamoh.foodie.payment.data.access.creditentry.adapter;

import com.mohamedbamoh.foodie.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditEntry;
import com.mohamedbamoh.foodie.payment.data.access.creditentry.mapper.CreditEntryDataAccessMapper;
import com.mohamedbamoh.foodie.payment.data.access.creditentry.repository.CreditEntryJpaRepository;
import com.mohamedbamoh.foodie.payment.service.domain.port.output.repository.CreditEntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CreditEntryRepositoryImpl implements CreditEntryRepository {

    private final CreditEntryJpaRepository creditEntryJpaRepository;
    private final CreditEntryDataAccessMapper creditEntryDataAccessMapper;

    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        return creditEntryDataAccessMapper.creditEntryEntityToCreditEntry(
                creditEntryJpaRepository.save(creditEntryDataAccessMapper.
                        creditEntryToCreditEntryEntity(creditEntry)));
    }

    @Override
    public Optional<CreditEntry> findByCustomerId(CustomerId customerId) {
        return creditEntryJpaRepository.findByCustomerId(customerId.getValue())
                .map(creditEntryDataAccessMapper::creditEntryEntityToCreditEntry);
    }
}
