package com.sparta_logistics.order.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderCreateResponse(
    String orderId,
    String productName,
    Integer quantity,
    String requestDescription
) {

}