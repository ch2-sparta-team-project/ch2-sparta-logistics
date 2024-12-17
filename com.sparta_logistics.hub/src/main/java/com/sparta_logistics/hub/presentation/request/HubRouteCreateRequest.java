package com.sparta_logistics.hub.presentation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubRouteCreateRequest(
    String sourceHubName,
    String destinationHubName,
    LocalTime duration,
    Double distance
) {
}
