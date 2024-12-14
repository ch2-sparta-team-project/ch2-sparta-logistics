package com.sparta_logistics.delivery.presentation.dto.request;

import com.sparta_logistics.delivery.application.dto.DeliveryManagerUpdateDto;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerStatus;
import jakarta.validation.constraints.NotNull;

public record DeliveryManagerUpdateRequest(
    @NotNull
    String hubId,
    @NotNull
    DeliveryManagerStatus status
) {
  public DeliveryManagerUpdateDto toDto() {
    return DeliveryManagerUpdateDto.builder()
        .hubId(this.hubId)
        .status(this.status)
        .build();
  }
}
