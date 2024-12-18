package com.sparta_logistics.order.presentation.filter;

import com.sparta_logistics.order.global.exception.ApplicationException;
import com.sparta_logistics.order.global.exception.ErrorCode;
import com.sparta_logistics.order.presentation.dto.RequestUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (request.getRequestURI().startsWith("/api/v1/orders/v3/api-docs") ||
        request.getRequestURI().startsWith("/api/v1/orders/swagger")){
      filterChain.doFilter(request, response);
    } else {
      //Mock 데이터 생성
      String userId = request.getHeader("ID_HEADER");
      String userName = request.getHeader("NAME_HEADER");
      String role = request.getHeader("ROLE_HEADER");
      String userSlackId = request.getHeader("SLACK_ID_HEADER");
      if (userId == null || userName == null || role == null) {
        throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
      }

      //Mock 유저 생성
      RequestUserDetails mockUserDetails = new RequestUserDetails(userId, userName, role, userSlackId);

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
