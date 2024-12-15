package com.sparta_logistics.hub.presentation.request;

public record HubRouteReadRequest (
    String sourceHubName,
    String destinationHubName
){

}
