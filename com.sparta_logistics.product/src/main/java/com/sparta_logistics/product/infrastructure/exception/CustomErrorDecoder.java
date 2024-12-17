package com.sparta_logistics.product.infrastructure.exception;

import com.sparta_logistics.product.global.exception.ApplicationException;
import com.sparta_logistics.product.global.exception.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    return switch (response.status()) {
      case 400 -> new ApplicationException(ErrorCode.INVALID_VALUE_EXCEPTION);
      case 404 -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION);
      default -> new ApplicationException(ErrorCode.INTERNAL_SERVER_EXCEPTION);
    };
  }
}
