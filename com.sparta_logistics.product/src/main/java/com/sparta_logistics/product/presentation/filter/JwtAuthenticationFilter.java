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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String userId = UUID.randomUUID().toString();
    String userName = "user";
    List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("MASTER"));

    UserDetails mockUserDetails = new RequestUserDetails(userId, userName, authorities);

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(mockUserDetails, null, authorities);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}
