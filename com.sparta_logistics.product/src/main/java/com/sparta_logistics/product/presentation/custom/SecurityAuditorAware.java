package com.sparta_logistics.product.presentation.custom;

import com.sparta_logistics.product.presentation.dto.RequestUserDetails;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    RequestUserDetails userDetails = (RequestUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    if (userDetails != null) {
      return Optional.of(userDetails.getUsername());
    }

    return Optional.of("anonymousUser");
  }
}
