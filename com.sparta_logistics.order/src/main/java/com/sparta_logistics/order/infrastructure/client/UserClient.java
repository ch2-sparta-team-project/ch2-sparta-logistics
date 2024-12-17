package com.sparta_logistics.order.infrastructure.client;

import com.sparta_logistics.order.infrastructure.client.dto.user.AuthResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface UserClient {

  // 사용자 정보 단일 조회(Master)
  @GetMapping("/api/v1/auth/users/{userId}")
  ResponseEntity<AuthResponseDto> usersGetInfoForMaster(
      @PathVariable UUID userId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-User-Id") String apiCallUserId,
      @RequestHeader("X-User-Name") String userName
  );
}
