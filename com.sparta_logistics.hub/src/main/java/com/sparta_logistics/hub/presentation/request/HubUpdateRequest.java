package com.sparta_logistics.hub.presentation.request;

import java.util.UUID;

public record HubUpdateRequest (
    UUID userId,
    String name,
    String address,
    Double longitude,
    Double latitude
){

}
