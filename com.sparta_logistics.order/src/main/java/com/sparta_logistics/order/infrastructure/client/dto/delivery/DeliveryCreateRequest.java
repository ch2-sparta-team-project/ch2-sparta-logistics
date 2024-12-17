package com.sparta_logistics.order.infrastructure.client.dto.delivery;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeliveryCreateRequest(
    String sourceHubId,
    String orderId,
    String address,
    String recipientName,
    String recipientSlackId
) {

}
