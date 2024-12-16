package com.sparta_logistics.delivery.infrastructure.dto;

import lombok.Builder;

@Builder
public record HubDto(
    String hubId,
    String name,
    String address,
    String centerHubId,
    Boolean isCenter,
    Double latitude,
    Double longitude
) {

}
