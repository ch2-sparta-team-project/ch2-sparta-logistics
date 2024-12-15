package com.sparta_logistics.ai.presentation;

import com.sparta_logistics.ai.presentation.dto.RequestUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String path = request.getRequestURI();

      if (path.equals("/actuator/refresh")) {
        filterChain.doFilter(request, response);
        return;
      }

      // 헤더 값 읽기
      String userId = request.getHeader("X-User-Id");
      String userName = request.getHeader("X-User-Name");
      String role = request.getHeader("X-User-Role");

      // 헤더 값 검증
      if (userId == null || userName == null || role == null) {
        throw new IllegalArgumentException("Required headers are missing");
      }

      // 권한 설정
      List<GrantedAuthority> authorities = List.of(
          new SimpleGrantedAuthority(role));

      UserDetails mockUserDetails = new RequestUserDetails(userId, userName, authorities);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(mockUserDetails, null, authorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);
    }catch (Exception ex) {
      log.error("Authentication failed: {}", ex.getMessage());
      SecurityContextHolder.clearContext();
      throw new ServletException("Authentication failed: Missing headers");
    }
  }
}
