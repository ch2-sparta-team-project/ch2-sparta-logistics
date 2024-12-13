package com.sparta_logistics.order.presentation.custom;

import com.sparta_logistics.order.presentation.dto.RequestUserDetails;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // SecurityContext에서 인증된 사용자 정보 가져오기
    RequestUserDetails userDetails = (RequestUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    // 인증된 사용자가 없다면, 예를 들어 "anonymousUser" 반환
    if (userDetails != null) {
      return Optional.of(userDetails.getUserId());
    }
    return Optional.of("anonymousUser");
  }
}
