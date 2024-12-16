package com.sparta_logistics.product.infrastructure.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HubDto {
  private UUID hubId;
  private String name;
  private String address;
  private Boolean isCenter;
}
