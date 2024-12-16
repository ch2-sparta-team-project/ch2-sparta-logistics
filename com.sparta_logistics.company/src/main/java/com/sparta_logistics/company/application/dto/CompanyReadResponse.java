package com.sparta_logistics.company.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.company.domain.model.Company;
import com.sparta_logistics.company.domain.model.CompanyType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyReadResponse {
  private UUID id;
  private UUID userId;
  private UUID hubId;
  private String name;
  private String address;
  @Enumerated(EnumType.STRING)
  private CompanyType companyType;
  private Double latitude;
  private Double longitude;
  private String phone;
  private String username;

  public static CompanyReadResponse of(Company company){
    return CompanyReadResponse.builder()
        .id(company.getId())
        .userId(company.getUserId())
        .hubId(company.getHubId())
        .name(company.getName())
        .address(company.getAddress())
        .companyType(company.getCompanyType())
        .latitude(company.getLatitude())
        .longitude(company.getLongitude())
        .phone(company.getPhone())
        .username(company.getUsername())
        .build();
  }
}
