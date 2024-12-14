package com.sparta_logistics.delivery.domain.model.enumerate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DeliveryManagerStatus {
  IN_DELIVERY("배송 중"),
  IN_RESTING("휴식 중"),
  NO_WORK("일 없음");

  private final String description;
}
