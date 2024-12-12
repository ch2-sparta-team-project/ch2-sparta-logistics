package com.sparta_logistics.order.application.port;

import com.sparta_logistics.order.infrastructure.client.dto.UserCreateDto;

public interface UserClientPort {

  UserCreateDto findUserInfoForCreateByUserId(String userId);

  String findUserNameByUserId(String userId);
}
