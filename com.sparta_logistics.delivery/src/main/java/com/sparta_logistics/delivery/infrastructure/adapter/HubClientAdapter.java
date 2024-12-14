package com.sparta_logistics.delivery.infrastructure.adapter;

import com.sparta_logistics.delivery.application.port.HubClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientAdapter implements HubClientPort {

  @Override
  public Boolean existsByHubId(String hubId) {
    return true;
  }
}
