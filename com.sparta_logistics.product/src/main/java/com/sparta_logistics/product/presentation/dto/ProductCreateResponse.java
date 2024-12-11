package com.sparta_logistics.product.presentation.dto;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProductCreateResponse {

  private UUID productId;

  public static ProductCreateResponse of(UUID productId) {
    return ProductCreateResponse.builder().
        productId(productId)
        .build();
  }
}
