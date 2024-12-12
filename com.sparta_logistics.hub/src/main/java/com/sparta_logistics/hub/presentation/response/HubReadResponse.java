package com.sparta_logistics.hub.presentation.response;

import com.sparta_logistics.hub.domain.model.Hub;
import java.util.UUID;
import lombok.Builder;

@Builder
public record HubReadResponse(
    UUID hubId,
    String name,
    String address,
    Boolean isCenter
) {

  public static HubReadResponse buildResponseByEntity(Hub hub) {
    return HubReadResponse.builder()
        .hubId(hub.getId())
        .name(hub.getName())
        .address(hub.getAddress())
        .isCenter(hub.getIsCenter())
        .build();
  }
}
