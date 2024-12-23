package com.sparta_logistics.company.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Hub-service")
public interface HubClient {

  @GetMapping("/api/v1/hubs/exist")
  Boolean isHubExist(@RequestParam String hubId);
}
