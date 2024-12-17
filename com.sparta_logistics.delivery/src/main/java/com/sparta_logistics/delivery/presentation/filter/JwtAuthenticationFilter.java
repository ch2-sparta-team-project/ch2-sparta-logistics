package com.sparta_logistics.delivery.presentation.filter;

import com.sparta_logistics.delivery.global.exception.ApplicationException;
import com.sparta_logistics.delivery.global.exception.ErrorCode;
import com.sparta_logistics.delivery.presentation.dto.auth.RequestUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (request.getRequestURI().startsWith("/api/v1/deliveries/v3/api-docs") ||
        request.getRequestURI().startsWith("/api/v1/deliveries/swagger")){
      filterChain.doFilter(request, response);
    } else {
      //Mock 데이터 생성
      String userId = request.getHeader("X-User-Id");
      String userName = request.getHeader("X-User-Name");
      String role = request.getHeader("X-User-Role");

      if (userId == null || userName == null || role == null) {
        throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
      }

      //Mock 유저 생성
      RequestUserDetails mockUserDetails = new RequestUserDetails(userId, userName, role);

      // Authentication 객체 생성
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(mockUserDetails, null, mockUserDetails.getAuthorities());

      // SecurityContext에 인증 정보 설정
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // 필터 체인 진행
      filterChain.doFilter(request, response);
    }
  }
}
