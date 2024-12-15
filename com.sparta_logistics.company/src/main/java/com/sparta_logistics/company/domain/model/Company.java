package com.sparta_logistics.company.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_company")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "p_company_id")
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

  public static Company createCompany(UUID userId, UUID hubId, String name, String address,
      CompanyType companyType, Double latitude, Double longitude,  String phone, String username) {
    return Company.builder()
        .userId(userId)
        .hubId(hubId)
        .name(name)
        .address(address)
        .companyType(companyType)
        .latitude(latitude)
        .longitude(longitude)
        .phone(phone)
        .username(username).build();
  }
}
