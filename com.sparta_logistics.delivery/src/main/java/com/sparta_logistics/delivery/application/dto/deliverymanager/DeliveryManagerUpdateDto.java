package com.sparta_logistics.delivery.application.dto.deliverymanager;

import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerStatus;
import lombok.Builder;

@Builder
public record DeliveryManagerUpdateDto(
    String hubId,
    DeliveryManagerStatus status
) {

}
