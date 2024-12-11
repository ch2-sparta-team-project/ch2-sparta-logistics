package com.sparta_logistics.product.presentation.dto;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductUpdateResponse {

  private UUID productId;

  public static ProductUpdateResponse of(UUID productId) {
    return ProductUpdateResponse.builder()
        .productId(productId)
        .build();
  }
}
