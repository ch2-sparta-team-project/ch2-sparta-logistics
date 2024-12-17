package com.sparta_logistics.hub.application.dtos;

public record HubDto(
    String name,
    String address,
    Double longitude,
    Double latitude,
    Boolean isCenter
) {

}
