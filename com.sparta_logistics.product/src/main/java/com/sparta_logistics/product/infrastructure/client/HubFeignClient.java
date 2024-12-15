package com.sparta_logistics.product.infrastructure.client;

import com.sparta_logistics.product.infrastructure.config.FeignClientConfig;
import com.sparta_logistics.product.infrastructure.dto.HubDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "hub-service",
    configuration = FeignClientConfig.class
)
public interface HubFeignClient {
  @GetMapping("/api/v1/hubs/{hubId}")
  HubDto readHub(@PathVariable("hubId")UUID hubId);
}
