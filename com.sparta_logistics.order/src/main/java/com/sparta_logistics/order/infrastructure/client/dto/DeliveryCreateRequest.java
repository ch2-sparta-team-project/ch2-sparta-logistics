package com.sparta_logistics.order.infrastructure.client.dto;

import lombok.Builder;

@Builder
public record DeliveryCreateRequest(
    String sourceHubId,
    String orderId,
    String address,
    String recipientName,
    String recipientSlackId
) {

}
