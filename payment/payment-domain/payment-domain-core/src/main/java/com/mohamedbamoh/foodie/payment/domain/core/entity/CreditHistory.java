package com.mohamedbamoh.foodie.payment.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.common.domain.valueobject.Money;
import com.mohamedbamoh.foodie.payment.domain.core.valueobject.TransactionType;
import com.mohamedbamoh.foodie.payment.domain.core.valueobject.CreditHistoryId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreditHistory extends BaseEntity<CreditHistoryId> {

    private final CustomerId customerId;
    private final Money amount;
    private final TransactionType transactionType;

    @Builder
    private CreditHistory(CreditHistoryId creditHistoryId, CustomerId customerId, Money amount, TransactionType transactionType) {
        super.setId(creditHistoryId);
        this.customerId = customerId;
        this.amount = amount;
        this.transactionType = transactionType;
    }
}
