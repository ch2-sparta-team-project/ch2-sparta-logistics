package com.sparta_logistics.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta_logistics.auth.Dto.SignInRequestDto;
import com.sparta_logistics.auth.Entity.Role;
import com.sparta_logistics.auth.Entity.User;
import com.sparta_logistics.auth.Security.JwtUtil;
import com.sparta_logistics.auth.Security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    setFilterProcessesUrl("/auth/login");
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
    String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
    Role role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
    String slackId = ((UserDetailsImpl) authResult.getPrincipal()).getSlackId();

    String token = jwtUtil.generateToken(username, role, slackId);
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    new ObjectMapper().writeValue(response.getWriter(), "로그인 성공");
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