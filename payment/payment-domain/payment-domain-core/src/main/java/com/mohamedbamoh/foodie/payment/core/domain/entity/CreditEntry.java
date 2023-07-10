package com.mohamedbamoh.foodie.payment.core.domain.entity;

import com.mohamedbamoh.foodie.domain.entity.BaseEntity;
import com.mohamedbamoh.foodie.domain.valueobject.CustomerId;
import com.mohamedbamoh.foodie.domain.valueobject.Money;
import com.mohamedbamoh.foodie.payment.core.domain.valueobject.CreditEntryId;
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
        totalCreditAmount = totalCreditAmount.substruct(amount);
    }
}
