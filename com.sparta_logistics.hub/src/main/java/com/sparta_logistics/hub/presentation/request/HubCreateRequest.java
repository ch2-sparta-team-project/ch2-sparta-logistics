package com.sparta_logistics.hub.presentation.request;

public record HubCreateRequest(
    String name,
    String address,
    Double longitude,
    Double latitude,
    Boolean isCenter
) {

}
