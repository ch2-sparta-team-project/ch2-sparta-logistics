package com.sparta_logistics.order.presentation.filter;

import com.sparta_logistics.order.presentation.dto.RequestUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    //Mock 데이터 생성
    String userId = UUID.randomUUID().toString();
    String userName = "강찬욱";
    List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("MASTER"));

    //Mock 유저 생성
    UserDetails mockUserDetails = new RequestUserDetails(userId, userName, authorities);

    // Authentication 객체 생성
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(mockUserDetails, null, authorities);

    // SecurityContext에 인증 정보 설정
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 필터 체인 진행
    filterChain.doFilter(request, response);
  }
}
