package com.sparta_logistics.auth.Dto;

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
  private Object data;

  public AuthResponseDto(String message, Integer statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public AuthResponseDto(String message, Integer statusCode, Object data) {
    this.message = message;
    this.statusCode = statusCode;
    this.data = data;
  }


}
