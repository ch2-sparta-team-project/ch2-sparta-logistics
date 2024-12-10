package com.sparta_logistics.order.application.dto;

import lombok.Builder;

@Builder
public record CreateDeliveryReq(
    String sourceHubId,
    String orderId,
    String address,
    String recipientName,
    String recipientSlackId
) {

}
