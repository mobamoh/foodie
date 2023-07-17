package com.mohamedbamoh.foodie.payment.domain.core.entity;

import com.mohamedbamoh.foodie.common.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.common.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.common.domain.valueobject.Money;
import com.mohamedbamoh.foodie.payment.domain.core.valueobject.CreditEntryId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreditEntry extends BaseEntity<CreditEntryId> {

    private final CustomerId customerId;
    private Money totalCreditAmount;

    @Builder
    private CreditEntry(CreditEntryId creditEntryId, CustomerId customerId, Money totalCreditAmount) {
        super.setId(creditEntryId);
        this.customerId = customerId;
        this.totalCreditAmount = totalCreditAmount;
    }

    public void addCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.add(amount);
    }

    public void subtractCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }
}
