package com.sparta_logistics.company.application.client;

import com.sparta_logistics.company.application.dto.HubReadResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Hub-service")
public interface HubClient {

  @GetMapping("/{hub_id}")
  HubReadResponse getHub(@PathVariable(value = "hub_id") UUID hubId);
}
