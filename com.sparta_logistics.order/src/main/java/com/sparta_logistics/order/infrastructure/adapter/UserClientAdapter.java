package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.infrastructure.client.UserClient;
import com.sparta_logistics.order.application.port.UserClientPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientAdapter implements UserClientPort {

  private final UserClient userClient;

  @Override
  public String findUserSlackIdByUserId(String userId) {
    return userClient.usersGetInfoForMaster(UUID.fromString(userId)
        , "ROLE_MASTER","3a03bfe9-1fc5-4cdc-816d-9acd8d9b6ec8","r")
        .getBody()
        .getData()
        .getSlackId();
  }
}
