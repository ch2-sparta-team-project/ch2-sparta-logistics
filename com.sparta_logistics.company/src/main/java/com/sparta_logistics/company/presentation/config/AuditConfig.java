package com.sparta_logistics.company.presentation.config;

import com.sparta_logistics.company.presentation.custom.SecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditConfig {

  @Bean
  public SecurityAuditorAware securityAuditorAware() {
    return new SecurityAuditorAware();
  }
}
