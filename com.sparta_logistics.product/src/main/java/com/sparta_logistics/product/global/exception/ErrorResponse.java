package com.sparta_logistics.product.global.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private LocalDateTime timestamp;
  private Integer code;
  private String message;

  public ErrorResponse(ErrorCode errorcode) {
    this(LocalDateTime.now(), errorcode.getCode(), errorcode.getMessage());
  }

  public ErrorResponse(String message) {
    this(LocalDateTime.now(), ErrorCode.INTERNAL_SERVER_EXCEPTION.getCode(), message);
  }

  public ErrorResponse(ErrorCode errorcode, String message) {
    this(LocalDateTime.now(), errorcode.getCode(), message);
  }
}
