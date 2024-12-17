package com.sparta_logistics.delivery.infrastructure.dto;

import lombok.Builder;

@Builder
public record HubDto(
    String hubId,
    String name,
    String address,
    String centerHubId,
    Boolean isCenter,
    Double latitude,
    Double longitude
) {
    public static HubDto from(HubReadResponse hubReadResponse) {
      return HubDto.builder()
          .hubId(hubReadResponse.hubId().toString())
          .name(hubReadResponse.name())
          .address(hubReadResponse.address())
          .centerHubId(hubReadResponse.centerHubId().toString())
          .isCenter(hubReadResponse.isCenter())
          .latitude(hubReadResponse.latitude())
          .longitude(hubReadResponse.longitude())
          .build();
    }
}
