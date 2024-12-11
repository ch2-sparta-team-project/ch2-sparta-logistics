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
public class CreateProductResponse {

  private UUID productId;

  public static CreateProductResponse of(UUID productId) {
    return CreateProductResponse.builder().
        productId(productId)
        .build();
  }
}
