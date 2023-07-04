package com.mohamedbamoh.foodie.order.service.domain.dto.track;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class TrackOrderQuery {
    @NotNull
    private final UUID orderTrackingId;
}
