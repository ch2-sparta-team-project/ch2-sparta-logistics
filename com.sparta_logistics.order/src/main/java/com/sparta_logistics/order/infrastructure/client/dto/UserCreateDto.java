package com.sparta_logistics.order.infrastructure.client.dto;

import lombok.Builder;

@Builder
public record UserCreateDto(
    String name,
    String slackId
) {

}
