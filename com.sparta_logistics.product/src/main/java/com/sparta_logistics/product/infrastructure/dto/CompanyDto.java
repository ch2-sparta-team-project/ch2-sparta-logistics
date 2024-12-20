package com.sparta_logistics.product.infrastructure.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
  private UUID id;
  private UUID userId;
  private UUID hubId;
  private String name;
  private String address;
  private String phone;
}
