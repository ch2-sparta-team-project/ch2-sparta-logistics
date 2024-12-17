package com.sparta_logistics.order.application.dto;

import lombok.Builder;

@Builder
public record OrderCreateDto(
    String supplierCompanyId,
    String productId,
    String productName,
    Integer quantity,
    String requestDescription,
    Boolean isRefunded
) {

}
