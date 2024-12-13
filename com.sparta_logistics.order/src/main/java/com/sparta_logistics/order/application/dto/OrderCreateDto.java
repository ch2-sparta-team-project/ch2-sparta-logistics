package com.sparta_logistics.order.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class OrderCreateDto {
  private String supplierCompanyId;
  private String userId;
  private String productId;
  private String productName;
  private Integer quantity;
  private String requestDescription;
  private Boolean isRefunded;
}
