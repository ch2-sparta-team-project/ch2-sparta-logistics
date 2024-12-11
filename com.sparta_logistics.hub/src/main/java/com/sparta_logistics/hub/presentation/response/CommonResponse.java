package com.sparta_logistics.hub.presentation.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;


@Builder(access = AccessLevel.PRIVATE)
public record CommonResponse<T>(
    LocalDateTime timeStamp,
    Integer code,
    String message,
    T data) {

}
