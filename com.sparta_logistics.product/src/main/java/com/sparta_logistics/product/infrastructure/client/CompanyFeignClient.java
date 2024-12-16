package com.sparta_logistics.product.infrastructure.client;

import com.sparta_logistics.product.infrastructure.config.FeignClientConfig;
import com.sparta_logistics.product.infrastructure.dto.CompanyDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "company-service",
    configuration = FeignClientConfig.class
)
public interface CompanyFeignClient {
  @GetMapping("/api/v1/companies/{companyId}")
  CompanyDto readCompany(@PathVariable("companyId") UUID companyId);
}
