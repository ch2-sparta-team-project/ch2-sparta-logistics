package com.sparta_logistics.order.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderReadResponse(
    String supplierCompanyName,
    String receiverCompanyName,
    String username,
    String productName,

    String deliveryId,
    Integer quantity,
    String requestDescription,
    Boolean isRefunded
) {

}
