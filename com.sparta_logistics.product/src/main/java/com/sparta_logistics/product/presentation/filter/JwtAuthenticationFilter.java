package com.sparta_logistics.product.presentation.filter;

import com.sparta_logistics.product.presentation.dto.RequestUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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

    String userId = request.getHeader("X-User-Id");
    String userName = request.getHeader("X-User-Name");
    String role = request.getHeader("X-User-Role");

    List<GrantedAuthority> authorities = Arrays.asList(
        new SimpleGrantedAuthority(role));

    UserDetails mockUserDetails = new RequestUserDetails(userId, userName, authorities);

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(mockUserDetails, null, authorities);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}