package com.sparta_logistics.slack.presentation.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

  private String message;
  private Integer statusCode;
  private Object data;

  public ResponseDto(String message, Integer statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public ResponseDto(String message, Integer statusCode, Object data) {
    this.message = message;
    this.statusCode = statusCode;
    this.data = data;
  }


}
