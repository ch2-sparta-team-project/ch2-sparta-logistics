package com.sparta_logistics.auth.Entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum Status {

  Before_delivery("1"),// 배송 전
  In_delivery("2"), // 배송 중
  Delivery_completed("3"), // 배송 완료
  Not_assigned("4"); // 배송 미배정

  private final String status;

  Status(String status) {
    this.status = status;
  }

  public SimpleGrantedAuthority toAuthority() {
    return new SimpleGrantedAuthority(status);
  }

}
