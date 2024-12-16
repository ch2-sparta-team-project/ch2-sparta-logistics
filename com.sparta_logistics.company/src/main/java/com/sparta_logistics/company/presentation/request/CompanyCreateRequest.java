package com.sparta_logistics.company.presentation.request;

import com.sparta_logistics.company.domain.model.CompanyType;
import java.util.UUID;

public record CompanyCreateRequest(
    UUID hubId,
    String name,
    String address,
    CompanyType companyType,
    Double latitude,
    Double longitude,
    String phone
) {

}
