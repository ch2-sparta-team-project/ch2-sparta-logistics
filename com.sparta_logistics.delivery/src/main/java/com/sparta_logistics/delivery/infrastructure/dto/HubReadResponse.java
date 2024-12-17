package com.sparta_logistics.delivery.infrastructure.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.UUID;
import lombok.Builder;


@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubReadResponse(
    UUID userId,
    UUID hubId,
    String name,
    String address,
    Boolean isCenter,
    Double longitude,
    Double latitude,
    UUID centerHubId
) {

}
