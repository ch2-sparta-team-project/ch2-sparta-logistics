package com.sparta_logistics.auth.Dto;

import com.sparta_logistics.auth.Entity.Role;
import com.sparta_logistics.auth.Entity.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignUpRequestDto {

  private String username;
  private String password;
  private String slackId;
  private Role role;

  public User toUser(PasswordEncoder passwordEncoder) {
    return User.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .slackId(slackId)
        .role(role)
        .build();
  }
}
