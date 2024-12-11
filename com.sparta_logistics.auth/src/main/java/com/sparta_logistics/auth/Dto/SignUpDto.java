package com.sparta_logistics.auth.Dto;

import com.sparta_logistics.auth.Entity.Role;
import com.sparta_logistics.auth.Entity.Status;
import lombok.Getter;

@Getter
public class SignUpDto {

  private String username;
  private String password;
  private String slackId;
  private Role role;
  private Status status;

}
