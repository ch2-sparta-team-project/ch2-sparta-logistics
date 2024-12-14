package com.sparta_logistics.delivery.presentation.dto;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class RequestUserDetails implements UserDetails {

  @Getter
  private final String userId;
  private final String username;
  @Getter
  private final String role;

  public RequestUserDetails(String userId, String username, String role) {
    this.userId = userId;
    this.username = username;
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.<GrantedAuthority>of(new SimpleGrantedAuthority(role));
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return "";
  }
}
