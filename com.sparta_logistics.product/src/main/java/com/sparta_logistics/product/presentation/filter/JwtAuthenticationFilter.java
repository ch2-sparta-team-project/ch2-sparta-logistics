package com.sparta_logistics.product.presentation.filter;

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
import org.springframework.http.HttpStatus;
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
    String path = request.getRequestURI();

    // 헤더 값 읽기
    String userId = request.getHeader("X-User-Id");
    String userName = request.getHeader("X-User-Name");
    String role = request.getHeader("X-User-Role");

    UserDetails userDetails;
    List<GrantedAuthority> authorities;
    // 헤더 값 검증
    if (userId == null || userName == null || role == null) {
      authorities = List.of(new SimpleGrantedAuthority("MASTER"));
      userDetails = new RequestUserDetails("mock", "mock", authorities);
    } else {
      authorities = List.of(new SimpleGrantedAuthority(role));
      userDetails = new RequestUserDetails(userId, userName, authorities);
    }

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, authorities);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
