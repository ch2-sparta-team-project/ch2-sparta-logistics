package com.sparta_logistics.delivery.infrastructure.client;

import com.sparta_logistics.delivery.infrastructure.dto.CompanyReadResponse;
import com.sparta_logistics.delivery.infrastructure.dto.CustomPage;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "company-service")
public interface CompanyClient {

  // 전체 권한
  @GetMapping("api/v1/companies")
  ResponseEntity<CustomPage<CompanyReadResponse>> searchCompanies(
      @RequestParam("id") UUID id,
      @RequestParam("userId") UUID userId,
      @PageableDefault Pageable pageable,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-User-Id") String apiCallUserId,
      @RequestHeader("X-User-Name") String userName
  );

}
