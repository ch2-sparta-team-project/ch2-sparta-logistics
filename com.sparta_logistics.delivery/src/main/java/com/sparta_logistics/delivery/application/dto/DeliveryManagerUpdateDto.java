package com.sparta_logistics.delivery.application.dto;

import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;

@Builder
public record DeliveryManagerUpdateDto(
    String hubId,
    DeliveryManagerStatus status
) {
  
}
