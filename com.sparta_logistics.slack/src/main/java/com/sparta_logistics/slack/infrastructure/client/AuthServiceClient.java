package com.sparta_logistics.slack.infrastructure.client;

import com.sparta_logistics.slack.presentation.Dto.UserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

  @GetMapping("/api/v1/auth/users/token")
  ResponseEntity<UserInfoResponseDto> findInfo(@RequestHeader("Authorization") String token);

}
