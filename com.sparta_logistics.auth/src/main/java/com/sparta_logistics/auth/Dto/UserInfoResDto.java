package com.sparta_logistics.auth.Dto;

import com.sparta_logistics.auth.Entity.Role;
import lombok.Getter;

@Getter
public class UserInfoResDto {

  private String username;
  private String slackId;
  private Role role;

}
