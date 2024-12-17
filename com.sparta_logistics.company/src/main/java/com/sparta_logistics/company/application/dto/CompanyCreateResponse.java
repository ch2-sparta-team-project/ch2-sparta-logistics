package com.sparta_logistics.company.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.company.domain.model.Company;
import com.sparta_logistics.company.domain.model.CompanyType;
import java.util.UUID;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CompanyCreateResponse(
    UUID userId,
    UUID hubId,
    String name,
    String address,
    CompanyType companyType,
    Double latitude,
    Double longitude,
    String phone,
    String username
) {

  public CompanyCreateResponse(Company company) {
    this(company.getUserId(),
        company.getHubId(),
        company.getName(),
        company.getAddress(),
        company.getCompanyType(),
        company.getLatitude(),
        company.getLongitude(),
        company.getPhone(),
        company.getUsername());
  }

}
