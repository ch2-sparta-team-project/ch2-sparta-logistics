package com.sparta_logistics.order.application.dto;

import lombok.Builder;

@Builder
public record CreateOrderRes(
    String orderId,
    String productName,
    Integer quantity,
    String requestDescription
) {

}
