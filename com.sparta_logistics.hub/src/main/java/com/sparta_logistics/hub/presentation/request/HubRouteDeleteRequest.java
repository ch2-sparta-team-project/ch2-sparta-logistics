package com.sparta_logistics.hub.presentation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubRouteDeleteRequest(
    String sourceHubName,
    String destinationHubName
) {

}
