package com.sparta_logistics.order.infrastructure.client.dto.company;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CompanySearchRequest(
    UUID id,
    UUID userId
){
}
