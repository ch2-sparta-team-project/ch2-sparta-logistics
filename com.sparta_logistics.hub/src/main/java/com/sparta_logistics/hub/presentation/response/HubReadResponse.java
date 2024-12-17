package com.sparta_logistics.hub.presentation.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.hub.domain.model.Hub;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  public static HubReadResponse buildResponseByEntity(Hub hub) {
    UUID centerHubId = null;
    if (hub.getCenterHub() != null){
      centerHubId = hub.getCenterHub().getId();
    }
    return HubReadResponse.builder()
        .userId(hub.getUserId())
        .hubId(hub.getId())
        .name(hub.getName())
        .address(hub.getAddress())
        .isCenter(hub.getIsCenter())
        .longitude(hub.getLongitude())
        .latitude(hub.getLatitude())
        .centerHubId(centerHubId)
        .build();
  }
}
