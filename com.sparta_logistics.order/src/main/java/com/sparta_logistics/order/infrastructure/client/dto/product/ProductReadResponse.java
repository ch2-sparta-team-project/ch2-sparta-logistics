package com.sparta_logistics.order.infrastructure.client.dto.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductReadResponse {

  private UUID productId;
  private UUID companyId;
  private UUID hubId;
  private String productName;
  private Integer productStock;
  private String productImageUrl;
  private Long productPrice;

}
