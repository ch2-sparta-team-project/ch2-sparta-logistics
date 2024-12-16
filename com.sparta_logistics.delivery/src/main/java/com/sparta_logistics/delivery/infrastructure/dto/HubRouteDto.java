package com.sparta_logistics.delivery.infrastructure.dto;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.StringTokenizer;
import lombok.Builder;

@Builder
public record HubRouteDto(
    String sourceHubId,
    String destinationHubId,
    Double distance,
    Long duration
) {

  public static HubRouteDto from(HubRouteReadResponse res){
    StringTokenizer st = new StringTokenizer(res.duration().toString(), ":");
    int hour = Integer.parseInt(st.nextToken());
    int minute = Integer.parseInt(st.nextToken());
    Long duration = hour * 60L + minute;
    return HubRouteDto.builder()
        .sourceHubId(res.sourceHubId().toString())
        .destinationHubId(res.destinationHubId().toString())
        .distance(res.distance())
        .duration(duration)
        .build();
  }
}
