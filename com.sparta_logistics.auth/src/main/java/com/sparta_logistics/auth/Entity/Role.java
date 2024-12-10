package com.sparta_logistics.auth.Entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum Role {

  MASTER("ROLE_MASTER"),//
  HUB_MANAGER("ROLE_HUB_MANAGER"),
  HUB_TO_HUB_DELIVERY("ROLE_HUB_TO_HUB_DELIVERY"),
  HUB_TO_COMPANY_DELIVERY("ROLE_HUB_TO_COMPANY_DELIVERY"),
  COMPANY_MANAGER("ROLE_COMPANY_MANAGER");

  private final String role;

  Role(String role) {
    this.role = role;
  }

  public SimpleGrantedAuthority toAuthority() {
    return new SimpleGrantedAuthority(role);
  }


}
