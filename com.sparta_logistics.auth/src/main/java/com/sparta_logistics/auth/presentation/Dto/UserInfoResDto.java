package com.sparta_logistics.auth.presentation.Dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.auth.domain.model.Role;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoResDto {

  private String userName;
  private String slackId;
  private Role role;

}
