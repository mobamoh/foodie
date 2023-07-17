package com.mohamedbamoh.foodie.payment.data.access.credithistory.mapper;

import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.common.domain.valueobject.Money;
import com.mohamedbamoh.foodie.payment.domain.core.entity.CreditHistory;
import com.mohamedbamoh.foodie.payment.domain.core.valueobject.CreditHistoryId;
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
