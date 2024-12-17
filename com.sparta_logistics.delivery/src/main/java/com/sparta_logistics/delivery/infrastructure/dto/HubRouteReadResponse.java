package com.sparta_logistics.delivery.infrastructure.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubRouteReadResponse(
    String sourceHubName,
    String destinationHubName,
    UUID sourceHubId,
    UUID destinationHubId,
    LocalTime duration,
    Double distance
) {

}
