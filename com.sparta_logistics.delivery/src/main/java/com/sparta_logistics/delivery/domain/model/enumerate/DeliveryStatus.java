package com.sparta_logistics.delivery.domain.model.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
  WAITING_AT_HUB("허브에서 대기 중"),
  MOVING_TO_HUB("허브 이동 중"),
  ARRIVED_AT_HUB("허브 도착"),
  OUT_FOR_DELIVERY("배송 출발"),
  DELIVERED("배송 완료"),
  RETURNED("반품 완료");

  private final String description;
}
