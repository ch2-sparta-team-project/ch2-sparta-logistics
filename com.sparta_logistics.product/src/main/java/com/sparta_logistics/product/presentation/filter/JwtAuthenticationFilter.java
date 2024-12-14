package com.sparta_logistics.product.presentation.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta_logistics.product.presentation.dto.RequestUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      // 헤더 값 읽기
      String userId = request.getHeader("X-User-Id");
      String userName = request.getHeader("X-User-Name");
      String role = request.getHeader("X-User-Role");

      // 헤더 값 검증
      if (userId == null || userName == null || role == null) {
        throw new IllegalArgumentException("Required headers are missing");
      }

      // 권한 설정
      List<GrantedAuthority> authorities = Arrays.asList(
          new SimpleGrantedAuthority(role));

      UserDetails mockUserDetails = new RequestUserDetails(userId, userName, authorities);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(mockUserDetails, null, authorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);
    } catch (Exception ex) {
      log.error("Authentication failed: {}", ex.getMessage());
      // Security ExceptionTranslationFilter로 전달
      SecurityContextHolder.clearContext();
      sendErrorResponse(response, 2003, ex.getMessage());
    }
  }

  private void sendErrorResponse(HttpServletResponse response, int statusCode, String message)
      throws IOException {
    response.setStatus(statusCode);
    response.setContentType("application/json");

    // JSON 응답 생성
    String jsonResponse = String.format(
        "{\"timestamp\": \"%s\", \"code\": %d, \"message\": \"%s\"}",
        LocalDateTime.now(), statusCode, message
    );

    // 응답 전송
    response.getWriter().write(jsonResponse);
  }
}
