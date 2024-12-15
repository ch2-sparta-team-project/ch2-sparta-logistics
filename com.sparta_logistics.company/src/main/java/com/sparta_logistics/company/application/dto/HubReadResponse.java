package com.sparta_logistics.company.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubReadResponse(
    UUID hubId,
    String name,
    String address,
    Boolean isCenter
) {
}
