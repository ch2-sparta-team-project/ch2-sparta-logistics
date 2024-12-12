package com.sparta_logistics.order.application.port;

import com.sparta_logistics.order.infrastructure.client.dto.CompanyCreateDto;

public interface CompanyClientPort {

  CompanyCreateDto findCompanyInfoForCreateByUserId(String userId);

  String findCompanyAffiliationHubIdByUserId(String userId);

  String findCompanyNameByCompanyId(String companyId);
}
