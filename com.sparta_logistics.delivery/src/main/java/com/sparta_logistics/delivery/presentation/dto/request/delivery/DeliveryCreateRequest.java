package com.sparta_logistics.delivery.presentation.dto.request.delivery;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.delivery.application.dto.delivery.DeliveryCreateDto;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeliveryCreateRequest(
    String sourceHubId,
    String orderId,
    String address,
    String recipientName,
    String recipientSlackId
) {

  public DeliveryCreateDto toDto() {
    return DeliveryCreateDto.builder()
        .sourceHubId(this.sourceHubId)
        .orderId(this.orderId)
        .address(this.address)
        .recipientName(this.recipientName)
        .recipientSlackId(this.recipientSlackId)
        .build();
  }
}
