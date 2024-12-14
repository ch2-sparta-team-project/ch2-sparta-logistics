package com.sparta_logistics.delivery.domain.model.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
  WAITING_AT_DEPARTURE_HUB("출발 허브 대기 중"),
  MOVING_TO_HUB("도착 허브로 이동 중"),
  ARRIVED_AT_HUB("허브 도착"),
  MOVING_TO_COMPANY("업체로 이동 중"),
  DELIVERED("배송 완료"),
  RETURNED("반품 완료");

  private final String description;
}
