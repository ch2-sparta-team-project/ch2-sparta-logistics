package com.sparta_logistics.order.application.dto;

import lombok.Builder;

@Builder
public record OrderUpdateDto(
    String supplierCompanyId,
    String receiverCompanyId,
    String userId,
    String productId,
    String deliveryId,
    Integer quantity,
    String requestDescription,
    Boolean isRefunded
) {

}
