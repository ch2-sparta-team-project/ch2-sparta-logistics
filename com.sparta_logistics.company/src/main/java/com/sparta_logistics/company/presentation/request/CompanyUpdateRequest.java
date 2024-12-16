package com.sparta_logistics.company.presentation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.company.domain.model.CompanyType;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CompanyUpdateRequest(
    UUID userId,
    UUID hubId,
    String name,
    String address,
    CompanyType companyType,
    Double latitude,
    Double longitude,
    String phone
) {

}
