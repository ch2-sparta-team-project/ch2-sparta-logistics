package com.sparta_logistics.auth.presentation.Dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignUpForCompanyManagerRequestDto {

  @Pattern(
      regexp = "^[a-z0-9]{4,10}$",
      message = "아이디는 4~10자의 알파벳 소문자와 숫자로만 구성되어야 합니다."
  )
  
  private String userName;

  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$])[A-Za-z\\d@$!%*#?&]{8,15}$",
      message = "비밀번호는 8~15자의 알파벳 대소문자, 숫자, 특수문자를 포함해야 합니다."
  )
  private String password;
  private String slackId;



}
