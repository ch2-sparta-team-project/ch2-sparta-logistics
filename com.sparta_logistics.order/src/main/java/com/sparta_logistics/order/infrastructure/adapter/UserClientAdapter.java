package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.infrastructure.client.dto.UserCreateDto;
import com.sparta_logistics.order.application.port.UserClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientAdapter implements UserClientPort {

  @Override
  public UserCreateDto findUserInfoByUserId(String userId) {
    return new UserCreateDto("강찬욱", "kan0202@naver.com");
  }
}
