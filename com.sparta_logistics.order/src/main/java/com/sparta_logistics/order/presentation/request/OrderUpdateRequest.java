package com.sparta_logistics.order.presentation.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.order.application.dto.OrderUpdateDto;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderUpdateRequest(
    @NotNull
    String supplierCompanyId,
    @NotNull
    String receiverCompanyId,
    @NotNull
    String productId,
    @NotNull
    String deliveryId,
    @NotNull
    Integer quantity,
    @NotNull
    String requestDescription,
    @NotNull
    Boolean isRefunded
) {
    public OrderUpdateDto toDTO() {
        return OrderUpdateDto.builder()
            .supplierCompanyId(this.supplierCompanyId)
            .receiverCompanyId(this.receiverCompanyId)
            .productId(this.productId)
            .deliveryId(this.deliveryId)
            .quantity(this.quantity)
            .requestDescription(this.requestDescription)
            .isRefunded(this.isRefunded)
            .build();
    }

}
