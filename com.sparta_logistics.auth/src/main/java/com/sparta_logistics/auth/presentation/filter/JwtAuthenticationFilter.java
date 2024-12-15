package com.sparta_logistics.auth.presentation.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta_logistics.auth.presentation.Dto.SignInRequestDto;
import com.sparta_logistics.auth.domain.model.Role;
import com.sparta_logistics.auth.application.Security.JwtUtil;
import com.sparta_logistics.auth.application.Security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
    setFilterProcessesUrl("/api/v1/auth/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      log.info("로그인 시도");

      SignInRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
          SignInRequestDto.class);

      if (requestDto.getUserName() == null || requestDto.getUserName().isEmpty()) {
        throw new IllegalArgumentException("Username cannot be empty");
      }
      if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
        throw new IllegalArgumentException("Password cannot be empty");
      }

      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getUserName(),
              requestDto.getPassword(),
              null
          )
      );
    } catch (IOException e) {
      throw new RuntimeException("로그인 요청 실패!!", e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    log.info("로그인 성공 및 JWT 생성");
    UUID userId = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserId();
    String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
    Role role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
    String slackId = ((UserDetailsImpl) authResult.getPrincipal()).getSlackId();

    String token = jwtUtil.generateToken(userId, username, role, slackId);
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("accessToken", "Bearer " + token);
    responseBody.put("username", username);
    responseBody.put("role", role.toString());
    responseBody.put("slackId", slackId);


    new ObjectMapper().writeValue(response.getWriter(), responseBody);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) throws IOException {

    log.error("로그인 실패: " + failed.getMessage());
    response.setStatus(401);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("code", "UNAUTHORIZED");
    errorResponse.put("message", "로그인에 실패했습니다.");
    new ObjectMapper().writeValue(response.getWriter(), errorResponse);
  }

}
