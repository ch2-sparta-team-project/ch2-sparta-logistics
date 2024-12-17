package com.sparta_logistics.ai.infrastructure.config;

import com.sparta_logistics.ai.presentation.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(
                "/swagger-ui/**",       // Swagger UI 관련 정적 리소스
                "/v3/api-docs/**",      // OpenAPI 문서
                "/swagger-ui.html",     // Swagger HTML 경로
                "/swagger-resources/**", // Swagger 설정 리소스
                "/webjars/**"           // Swagger 정적 리소스
            ).permitAll()
            .anyRequest().permitAll()
        )
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }
}