package com.sparta_logistics.order.infrastructure.client.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDto {

  private String message;
  private Integer statusCode;
  private UserInfoResponseDto data;

}
