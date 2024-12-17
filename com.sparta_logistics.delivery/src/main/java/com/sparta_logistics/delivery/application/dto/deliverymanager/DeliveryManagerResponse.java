package com.sparta_logistics.delivery.application.dto.deliverymanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerStatus;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record DeliveryManagerResponse(
    String deliveryManagerId,
    String hubId,
    DeliveryManagerRole role,
    DeliveryManagerStatus status,
    Integer turn
) {

}
