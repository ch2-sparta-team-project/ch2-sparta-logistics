package com.sparta_logistics.hub.util;

public class RoleValidator {

  public static void validateIsMaster(String role) {
    if (role.equals("MASTER")) {
      throw new IllegalArgumentException("인가되지 않은 사용자입니다.");
    }
  }

  public static void validateIsHubManager(String role) {
    if (role.equals("HUB_MANAGER") || role.equals("MASTER")) {
      throw new IllegalArgumentException("인가되지 않은 사용자입니다.");
    }
  }

  public static void validateIsHubDeliveryManager(String role) {
    if (role.equals("HUB_TO_HUB_DELIVERY") || role.equals("MASTER")) {
      throw new IllegalArgumentException("인가되지 않은 사용자입니다.");
    }
  }

  public static void validateIsCompanyDeliveryManager(String role) {
    if (role.equals("HUB_TO_COMPANY_DELIVERY") || role.equals("MASTER")) {
      throw new IllegalArgumentException("인가되지 않은 사용자입니다.");
    }
  }

  public static void validateIsCompanyManager(String role) {
    if (role.equals("COMPANY_MANAGER") || role.equals("MASTER")) {
      throw new IllegalArgumentException("인가되지 않은 사용자입니다.");
    }
  }
}
