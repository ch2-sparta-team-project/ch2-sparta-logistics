package com.sparta_logistics.delivery.application.dto;

import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerStatus;
import lombok.Builder;

@Builder
public record DeliveryManagerCreateDto(
    String userId,
    String hubId,
    DeliveryManagerRole role,
    DeliveryManagerStatus status
) {

}
