package com.mohamedbamoh.foodie.order.domain.app.service.outbox.model.approval;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderApprovalEventProduct {
    @JsonProperty
    private String id;
    @JsonProperty
    private Integer quantity;
}

