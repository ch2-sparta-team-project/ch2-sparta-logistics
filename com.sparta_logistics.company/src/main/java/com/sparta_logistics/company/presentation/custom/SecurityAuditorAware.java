package com.sparta_logistics.company.presentation.custom;


import com.sparta_logistics.company.presentation.dto.RequestUserDetails;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // SecurityContext에서 인증된 사용자 정보 가져오기
    RequestUserDetails userDetails = (RequestUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return Optional.of(userDetails.getUserId());
  }
}
