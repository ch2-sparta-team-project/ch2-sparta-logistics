package com.sparta_logistics.order.presentation.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class RequestUserDetails implements UserDetails {

  @Getter
  private final String userId;
  private final String username;
  private final Collection<? extends GrantedAuthority> authorities;

  public RequestUserDetails(String userId, String username,
      Collection<? extends GrantedAuthority> authorities) {
    this.userId = userId;
    this.username = username;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
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
