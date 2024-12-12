package com.sparta_logistics.order.application.mapper;

import com.sparta_logistics.order.application.dto.OrderReadResponse;
import com.sparta_logistics.order.domain.model.Order;

public class OrderDtoMapper {

  public static OrderReadResponse toOrderReadResponse(
      Order order,
      String receiverCompanyName,
      String supplierCompanyName,
      String userName,
      String productName
  ) {
    return OrderReadResponse.builder()
        .orderId(order.getId())
        .receiverCompanyName(receiverCompanyName)
        .supplierCompanyName(supplierCompanyName)
        .userId(order.getUserId())
        .username(userName)
        .productName(productName)
        .productId(order.getProductId())
        .deliveryId(order.getDeliveryId())
        .quantity(order.getQuantity())
        .requestDescription(order.getRequestDescription())
        .isRefunded(order.getIsRefunded())
        .build();
  }
}
