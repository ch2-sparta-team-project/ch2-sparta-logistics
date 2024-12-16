package com.sparta_logistics.hub.presentation.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.hub.domain.model.Hub;
import java.util.UUID;
import lombok.Builder;


@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubReadResponse(
    UUID userId,
    UUID hubId,
    String name,
    String address,
    Boolean isCenter
) {

  public static HubReadResponse buildResponseByEntity(Hub hub) {
    return HubReadResponse.builder()
        .userId(hub.getUserId())
        .hubId(hub.getId())
        .name(hub.getName())
        .address(hub.getAddress())
        .isCenter(hub.getIsCenter())
        .build();
  }
}
