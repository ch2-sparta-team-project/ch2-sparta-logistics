package com.sparta_logistics.auth.presentation.Dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.auth.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignUpRequestDto {

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
  private Role role;

  public static SignUpRequestDto from(SignUpForCompanyManagerRequestDto requestDto) {
    return SignUpRequestDto.builder()
        .userName(requestDto.getUserName())
        .password(requestDto.getPassword())
        .slackId(requestDto.getSlackId())
        .role(Role.COMPANY_MANAGER)
        .build();
  }

}
