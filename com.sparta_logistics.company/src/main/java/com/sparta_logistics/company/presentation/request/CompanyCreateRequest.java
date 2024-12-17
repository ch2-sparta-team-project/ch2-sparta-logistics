package com.sparta_logistics.company.presentation.request;

import com.sparta_logistics.company.domain.model.CompanyType;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CompanyCreateRequest(
    @NotNull
    UUID hubId,
    @NotNull
    String name,
    @NotNull
    String address,
    @NotNull
    CompanyType companyType,
    @NotNull
    Double latitude,
    @NotNull
    Double longitude,
    @NotNull
    String phone
) {

}
