package com.sparta_logistics.product.presentation.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

  private UUID companyId;
  private UUID hubId;
  private String name;
  private Integer stock;
  private String imageUrl;
  private Long price;
}
