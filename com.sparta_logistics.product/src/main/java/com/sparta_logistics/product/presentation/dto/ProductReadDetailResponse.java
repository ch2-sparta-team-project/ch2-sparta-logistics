package com.sparta_logistics.product.presentation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.infrastructure.dto.CompanyDto;
import com.sparta_logistics.product.infrastructure.dto.HubDto;
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
public class ProductReadDetailResponse {

  private UUID productId;
  private String productName;
  private Integer productStock;
  private String productImageUrl;
  private Long productPrice;
  private CompanyReadResponse company;
  private HubReadResponse hub;

  public static ProductReadDetailResponse of(Product product, CompanyDto company, HubDto hub) {
    return ProductReadDetailResponse.builder()
        .productId(product.getId())
        .productName(product.getName())
        .productImageUrl(product.getImageUrl())
        .productStock(product.getStock())
        .productPrice(product.getPrice())
        .company(CompanyReadResponse.of(company))
        .hub(HubReadResponse.of(hub))
        .build();
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder(access = AccessLevel.PRIVATE)
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class CompanyReadResponse {

    private UUID companyId;
    private String companyName;
    private String companyAddress;

    public static CompanyReadResponse of(CompanyDto companyDto) {
      return CompanyReadResponse.builder()
          .companyId(companyDto.getId())
          .companyAddress(companyDto.getAddress())
          .companyName(companyDto.getName())
          .build();
    }
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder(access = AccessLevel.PRIVATE)
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class HubReadResponse {

    private UUID hubId;
    private String hubName;
    private String hubAddress;

    public static HubReadResponse of(HubDto hubDto) {
      return HubReadResponse.builder()
          .hubId(hubDto.getHubId())
          .hubName(hubDto.getName())
          .hubAddress(hubDto.getAddress())
          .build();
    }
  }
}
