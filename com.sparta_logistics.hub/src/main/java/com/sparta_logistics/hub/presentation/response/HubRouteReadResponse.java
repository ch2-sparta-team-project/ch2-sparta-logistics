package com.sparta_logistics.hub.presentation.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.hub.domain.model.HubRoute;
import java.time.LocalTime;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubRouteReadResponse (
    String sourceHubName,
    String destinationHubName,
    LocalTime duration,
    Double distance
) {

  public static HubRouteReadResponse buildResponseByEntity(HubRoute hubRoute){
    return HubRouteReadResponse.builder()
        .sourceHubName(hubRoute.getSourceHub().getName())
        .destinationHubName(hubRoute.getDestinationHub().getName())
        .duration(hubRoute.getDuration())
        .distance(hubRoute.getDistance())
        .build();
  }

}
