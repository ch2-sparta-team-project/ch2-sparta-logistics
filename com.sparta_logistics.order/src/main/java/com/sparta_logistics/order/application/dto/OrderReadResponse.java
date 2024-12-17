package com.sparta_logistics.order.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderReadResponse(
    String orderId,
    String supplierCompanyName,
    String receiverCompanyName,
    String username,
    String userId,
    String productName,
    String productId,
    String deliveryId,
    Integer quantity,
    String requestDescription,
    Boolean isRefunded
) {

}
