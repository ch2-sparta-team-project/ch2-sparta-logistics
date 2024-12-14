package com.sparta_logistics.delivery.infrastructure.adapter;

import com.sparta_logistics.delivery.application.port.UserClientPort;
import com.sparta_logistics.order.application.port.UserClientPort;
import com.sparta_logistics.order.infrastructure.client.dto.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientAdapter implements UserClientPort {
  @Override
  public Boolean existsByUserId(String userId) {
    return true;
  }
}
