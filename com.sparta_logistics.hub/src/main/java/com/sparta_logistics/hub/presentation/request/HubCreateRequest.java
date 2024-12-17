package com.sparta_logistics.hub.presentation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HubCreateRequest(
    @NotNull
    String name,
    @NotNull
    String address,
    @NotNull
    Double longitude,
    @NotNull
    Double latitude,
    @NotNull
    Boolean isCenter
) {

}
