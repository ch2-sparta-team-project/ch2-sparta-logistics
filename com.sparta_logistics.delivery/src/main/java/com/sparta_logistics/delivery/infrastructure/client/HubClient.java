package com.sparta_logistics.delivery.infrastructure.client;

import com.sparta_logistics.delivery.infrastructure.dto.HubReadResponse;
import com.sparta_logistics.delivery.infrastructure.dto.HubRouteReadResponse;
import com.sparta_logistics.delivery.infrastructure.dto.HubSearchRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service")
public interface HubClient {

  //허브 단일 조회
  @GetMapping("/api/v1/hubs/{hub_id}")
  HubReadResponse getHub(@PathVariable(value = "hub_id") UUID hubId);

  @GetMapping("/api/v1/hubs/all")
  List<HubReadResponse> getHubs();

  @GetMapping("/api/v1/hubs/hub_route/all")
  List<HubRouteReadResponse> readHubRoutes();
}
