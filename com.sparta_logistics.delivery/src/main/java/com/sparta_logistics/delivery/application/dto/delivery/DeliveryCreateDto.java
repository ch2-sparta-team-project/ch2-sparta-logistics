package com.sparta_logistics.delivery.application.dto.delivery;

import lombok.Builder;

@Builder
public record DeliveryCreateDto(
    String sourceHubId,
    String orderId,
    String address,
    String recipientName,
    String recipientSlackId
) {

}
