package com.sparta_logistics.product.presentation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProductUpdateRequest {
  private String productName;
  private Integer productStock;
  private String productImageUrl;
  private Long productPrice;
}
