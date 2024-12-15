package com.sparta_logistics.slack;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

  @GetMapping("/api/v1/auth/users/token")
  String sendInfo(@RequestHeader("Authorization") String token);
}
