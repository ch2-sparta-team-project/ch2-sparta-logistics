package com.sparta_logistics.hub.presentation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubRouteCreateRequest(
    @NotNull
    String sourceHubName,
    @NotNull
    String destinationHubName,
    @NotNull
    LocalTime duration,
    @NotNull
    Double distance
) {
}
