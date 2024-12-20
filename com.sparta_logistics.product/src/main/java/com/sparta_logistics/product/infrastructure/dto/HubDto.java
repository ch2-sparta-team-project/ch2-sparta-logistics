package com.sparta_logistics.product.infrastructure.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HubDto implements Serializable {
  private UUID hubId;
  private UUID userId;
  private String name;
  private String address;
  private Boolean isCenter;
}
