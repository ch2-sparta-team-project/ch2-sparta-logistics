package com.sparta_logistics.order.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record OrderCreateDto(
    String supplierCompanyId,
    String userId,
    String productId,
    String productName,
    Integer quantity,
    String requestDescription,
    Boolean isRefunded
) {

}
