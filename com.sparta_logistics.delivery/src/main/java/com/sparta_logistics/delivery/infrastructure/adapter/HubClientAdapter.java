package com.sparta_logistics.delivery.infrastructure.adapter;

import com.sparta_logistics.delivery.application.port.HubClientPort;
import com.sparta_logistics.delivery.infrastructure.client.HubClient;
import com.sparta_logistics.delivery.infrastructure.dto.HubDto;
import com.sparta_logistics.delivery.infrastructure.dto.HubReadResponse;
import com.sparta_logistics.delivery.infrastructure.dto.HubRouteDto;
import com.sparta_logistics.delivery.infrastructure.dto.HubRouteReadResponse;
import com.sparta_logistics.delivery.infrastructure.dto.HubSearchRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientAdapter implements HubClientPort {

  private final HubClient hubClient;

  @Override
  public Boolean existsByHubId(String hubId) {
    HubReadResponse hub = hubClient.getHub(UUID.fromString(hubId));
    return hub != null;
  }

  @Override
  public Map<String, HubDto> findHubAll() {
    List<HubReadResponse> hubs = hubClient.getHubs();
    return hubs.stream()
        .map(HubDto::from)
        .collect(Collectors.toMap(
            HubDto::hubId, // 키로 사용할 값
            hub -> hub     // 값으로 사용할 객체
        ));
  }

  @Override
  public Map<String, HubRouteDto> findHubRouteAll() {
    List<HubRouteReadResponse> hubRoutes = hubClient.readHubRoutes();
    return hubRoutes.stream()
        .map(HubRouteDto::from)
        .collect(Collectors.toMap(
            HubRouteDto::sourceHubId,
            hub -> hub
        ));
  }

}
