package com.sparta_logistics.product.presentation.dto;

import com.sparta_logistics.product.domain.model.Product;
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
public class ProductReadResponse {

  private UUID productId;
  private UUID companyId;
  private UUID hubId;
  private String productName;
  private Integer productStock;
  private String productImageUrl;
  private Long productPrice;

  public static ProductReadResponse of(Product product) {
    return ProductReadResponse.builder()
        .companyId(product.getCompanyId())
        .hubId(product.getHubId())
        .productId(product.getId())
        .productName(product.getName())
        .productImageUrl(product.getImageUrl())
        .productStock(product.getStock())
        .productPrice(product.getPrice())
        .build();
  }
}
