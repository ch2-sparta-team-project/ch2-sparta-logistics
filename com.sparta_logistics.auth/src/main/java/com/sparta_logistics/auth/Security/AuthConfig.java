package com.sparta_logistics.auth.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta_logistics.auth.jwt.JwtAuthenticationFilter;
import com.sparta_logistics.auth.jwt.JwtAuthorizationFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
// @EnableMethodSecurity(securedEnabled = true) 추후 접근권한 부여시 사용
public class AuthConfig {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationConfiguration authenticationConfiguration;


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
    filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
    return filter;
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(
                "/user/signUp",
                "/user/login"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(handler -> handler
            .authenticationEntryPoint((request, response, authException) -> {
              log.error("인증 에러: {}", authException.getMessage());
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.setContentType("application/json;charset=UTF-8");

              ObjectMapper objectMapper = new ObjectMapper();
              String jsonResponse = objectMapper.writeValueAsString(Map.of
                  ("code", "FORBIDDEN",
                      "message", "접근 권한이 없습니다.")
              );

              response.getWriter().write(jsonResponse);
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
              log.error("접근 거절: {}", accessDeniedException.getMessage());
              response.setStatus(HttpServletResponse.SC_FORBIDDEN);
              response.setContentType("application/json;charset=UTF-8");

              ObjectMapper objectMapper = new ObjectMapper();
              String jsonResponse = objectMapper.writeValueAsString(Map.of
                  ("code", "FORBIDDEN",
                      "message", "접근 권한이 없습니다."));

              response.getWriter().write(jsonResponse);
            })
        );

    return http.build();
  }

}