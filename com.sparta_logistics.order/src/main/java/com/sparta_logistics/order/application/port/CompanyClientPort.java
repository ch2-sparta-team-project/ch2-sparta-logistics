package com.sparta_logistics.order.application.port;

import com.sparta_logistics.order.infrastructure.client.dto.CompanyCreateDto;

public interface CompanyClientPort {

  CompanyCreateDto findCompanyInfoByUserId(String userId);
}
