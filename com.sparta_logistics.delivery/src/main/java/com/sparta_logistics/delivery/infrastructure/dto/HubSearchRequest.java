package com.sparta_logistics.delivery.infrastructure.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubSearchRequest(
    String name,
    String address,
    Boolean isCenter,
    String centerHubName
) {

}
