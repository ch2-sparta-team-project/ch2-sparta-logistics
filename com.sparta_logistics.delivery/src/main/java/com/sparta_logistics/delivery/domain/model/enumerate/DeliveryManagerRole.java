package com.sparta_logistics.delivery.domain.model.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryManagerRole {
  HUB_DELIVERY_MANAGER("허브 배송 관리자"),
  COMPANY_DELIVERY_MANAGER("업체 배송 관리자");

  private final String description;
}
