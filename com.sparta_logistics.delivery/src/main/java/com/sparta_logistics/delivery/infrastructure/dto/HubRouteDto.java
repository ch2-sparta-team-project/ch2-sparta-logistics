package com.sparta_logistics.delivery.infrastructure.dto;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Builder;

@Builder
public record HubRouteDto(
    String sourceHubName,
    String destinationHubName,
    Double distance,
    Long duration
) {
}
