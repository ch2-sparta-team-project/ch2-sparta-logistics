package com.sparta_logistics.order.application.port;

import com.sparta_logistics.order.infrastructure.client.dto.UserCreateDto;

public interface UserClientPort {

  UserCreateDto findUserInfoByUserId(String userId);
}
