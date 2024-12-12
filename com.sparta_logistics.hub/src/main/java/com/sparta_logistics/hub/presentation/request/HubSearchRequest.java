package com.sparta_logistics.hub.presentation.request;

public record HubSearchRequest(
    String name,
    String address,
    Boolean isCenter,
    String centerHubName
) {

}
