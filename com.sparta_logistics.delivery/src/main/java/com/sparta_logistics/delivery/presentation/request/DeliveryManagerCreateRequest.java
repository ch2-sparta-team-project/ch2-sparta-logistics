package com.sparta_logistics.delivery.presentation.request;

import com.sparta_logistics.delivery.application.dto.DeliveryManagerCreateDto;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerStatus;
import jakarta.validation.constraints.NotNull;

public record DeliveryManagerCreateRequest(
    @NotNull
    String userId,
    @NotNull
    String hubId,
    @NotNull
    DeliveryManagerRole role,
    @NotNull
    DeliveryManagerStatus status
) {
  public DeliveryManagerCreateDto toDto() {
    return DeliveryManagerCreateDto.builder()
        .userId(this.userId)
        .hubId(this.hubId)
        .role(this.role)
        .status(this.status)
        .build();
  }
}
