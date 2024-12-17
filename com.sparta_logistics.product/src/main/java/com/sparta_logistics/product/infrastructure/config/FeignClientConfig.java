package com.sparta_logistics.product.infrastructure.config;

import com.sparta_logistics.product.infrastructure.exception.CustomErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {
  @Value("${service.header.id}")
  private String userId;

  @Value("${service.header.username}")
  private String username;

  @Value("${service.header.role}")
  private String role;
  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("X-User-Id", userId);
      requestTemplate.header("X-User-Name", username);
      requestTemplate.header("X-User-Role", role);
    };
  }
  @Bean
  public ErrorDecoder errorDecoder() {
    return new CustomErrorDecoder();
  }
}
