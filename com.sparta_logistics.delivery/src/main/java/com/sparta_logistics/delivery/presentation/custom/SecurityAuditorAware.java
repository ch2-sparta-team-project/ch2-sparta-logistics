package com.sparta_logistics.delivery.presentation.custom;

import com.sparta_logistics.delivery.presentation.dto.auth.RequestUserDetails;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

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
