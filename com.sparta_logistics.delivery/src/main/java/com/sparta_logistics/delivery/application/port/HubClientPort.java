package com.sparta_logistics.delivery.application.port;

import com.sparta_logistics.delivery.infrastructure.dto.HubDto;
import com.sparta_logistics.delivery.infrastructure.dto.HubRouteDto;
import java.util.Map;

public interface HubClientPort {

  Boolean existsByHubId(String hubId);

  Map<String, HubDto> findHubAll();

  Map<String, HubRouteDto> findHubRouteAll();
}
