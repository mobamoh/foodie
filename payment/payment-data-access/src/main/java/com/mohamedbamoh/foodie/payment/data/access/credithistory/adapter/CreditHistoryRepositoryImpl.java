package com.mohamedbamoh.foodie.payment.data.access.credithistory.adapter;

import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.payment.domain.core.entity.CreditHistory;
import com.mohamedbamoh.foodie.payment.data.access.credithistory.mapper.CreditHistoryDataAccessMapper;
import com.mohamedbamoh.foodie.payment.data.access.credithistory.repository.CreditHistoryJpaRepository;
import com.mohamedbamoh.foodie.payment.domain.app.service.port.output.repository.CreditHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(creditHistoryJpaRepository.
                save(creditHistoryDataAccessMapper.creditHistoryToCreditHistoryEntity(creditHistory)));
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId) {
        return creditHistoryJpaRepository.findByCustomerId(customerId.getValue())
                .map(chl -> chl.stream()
                        .map(creditHistoryDataAccessMapper::creditHistoryEntityToCreditHistory)
                        .collect(Collectors.toList()));
    }
}
