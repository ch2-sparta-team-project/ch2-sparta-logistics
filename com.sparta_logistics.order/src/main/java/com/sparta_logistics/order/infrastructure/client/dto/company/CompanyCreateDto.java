package com.sparta_logistics.order.infrastructure.client.dto.company;

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
