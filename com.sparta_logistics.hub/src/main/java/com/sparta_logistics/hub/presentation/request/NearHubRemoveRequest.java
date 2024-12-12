package com.sparta_logistics.hub.presentation.request;

import java.util.List;
import java.util.UUID;

public record NearHubRemoveRequest(
    List<UUID> nearHubList
){

}
