package com.sparta_logistics.hub.presentation.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.hub.domain.model.Hub;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubCreateResponse(
    String name,
    String address,
    Double longitude,
    Double latitude,
    Boolean isCenter
) {

  public static HubCreateResponse buildResponseByEntity(Hub hub) {
    return HubCreateResponse.builder()
        .name(hub.getName())
        .address(hub.getAddress())
        .longitude(hub.getLongitude())
        .latitude(hub.getLatitude())
        .isCenter(hub.getIsCenter())
        .build();
  }
}
