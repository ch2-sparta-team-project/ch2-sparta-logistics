package com.sparta_logistics.order.infrastructure.client.dto.user;

import lombok.Builder;

@Builder
public record UserCreateDto(
    String name,
    String slackId
) {

}
