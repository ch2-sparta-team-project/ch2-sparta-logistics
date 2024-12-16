package com.sparta_logistics.ai.infrastructure.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class OpenAiConfig {
  @Value("${openai.api.key}")
  private String apiKey;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("Authorization", "Bearer " + apiKey);
      requestTemplate.header("Content-Type", "application/json");
    };
  }
}
