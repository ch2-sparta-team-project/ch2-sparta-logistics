package com.sparta_logistics.hub.presentation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubRouteDeleteRequest(
    @NotNull
    String sourceHubName,
    @NotNull
    String destinationHubName
) {

}
