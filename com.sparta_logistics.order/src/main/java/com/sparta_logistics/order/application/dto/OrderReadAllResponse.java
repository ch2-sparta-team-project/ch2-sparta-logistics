package com.sparta_logistics.order.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderReadAllResponse(
    String orderId,
    String supplierCompanyId,
    String receiverCompanyId,
    String username,
    String userId,
    String productId,
    String deliveryId,
    Integer quantity,
    String requestDescription,
    Boolean isRefunded
) {

}
