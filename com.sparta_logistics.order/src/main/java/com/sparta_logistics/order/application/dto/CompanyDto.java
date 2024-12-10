package com.sparta_logistics.order.application.dto;

import lombok.Builder;

@Builder
public record CompanyDto(
    String companyId,
    String hubId,
    String address,
    Double latitude,
    Double longitude
) {

}
