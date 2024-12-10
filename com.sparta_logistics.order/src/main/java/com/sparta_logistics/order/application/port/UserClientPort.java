package com.sparta_logistics.order.application.port;

import com.sparta_logistics.order.application.dto.UserDto;

public interface UserClientPort {

  UserDto findUserInfoByUserId(String userId);
}
