package com.sparta_logistics.order.infrastructure.client.dto;

import lombok.Builder;

@Builder
public record CompanyCreateDto(
    String companyId,
    String hubId,
    String address,
    Double latitude,
    Double longitude
) {

}
