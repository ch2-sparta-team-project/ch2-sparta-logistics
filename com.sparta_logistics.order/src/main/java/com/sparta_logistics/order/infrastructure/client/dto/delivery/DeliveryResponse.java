package com.sparta_logistics.order.infrastructure.client.dto.delivery;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record DeliveryResponse(
    String deliveryId
) {

}