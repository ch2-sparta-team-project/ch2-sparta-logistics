package com.sparta_logistics.order.presentation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.order.application.dto.OrderCreateDto;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderCreateRequest(
    @NotNull
    String supplierCompanyId, //프론트에서 전달
    @NotNull
    String productId, //프론트에서 전달
    @NotNull
    String productName, //프론트에서 전달
    @NotNull
    Integer quantity, //사용자 기입
    String requestDescription //사용자 기입
) {

  public OrderCreateDto toDTO() {
    return OrderCreateDto.builder()
        .supplierCompanyId(this.supplierCompanyId)
        .productId(this.productId)
        .productName(this.productName)
        .quantity(this.quantity)
        .requestDescription(this.requestDescription)
        .isRefunded(false)
        .build();
  }
}
