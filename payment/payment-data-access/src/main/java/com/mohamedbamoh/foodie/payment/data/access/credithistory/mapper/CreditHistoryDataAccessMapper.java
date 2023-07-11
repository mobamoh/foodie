package com.mohamedbamoh.foodie.payment.data.access.credithistory.mapper;

import com.mohamedbamoh.foodie.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.payment.core.domain.entity.CreditHistory;
import com.mohamedbamoh.foodie.payment.core.domain.valueobject.CreditHistoryId;
import com.mohamedbamoh.foodie.payment.data.access.credithistory.entity.CreditHistoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditHistoryDataAccessMapper {

    public CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity creditHistoryEntity) {
        return CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(creditHistoryEntity.getId()))
                .customerId(new CustomerId(creditHistoryEntity.getCustomerId()))
                .amount(new Money(creditHistoryEntity.getAmount()))
                .transactionType(creditHistoryEntity.getType())
                .build();
    }

    public CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory) {
        return CreditHistoryEntity.builder()
                .id(creditHistory.getId().getValue())
                .customerId(creditHistory.getCustomerId().getValue())
                .amount(creditHistory.getAmount().getAmount())
                .type(creditHistory.getTransactionType())
                .build();
    }
}
