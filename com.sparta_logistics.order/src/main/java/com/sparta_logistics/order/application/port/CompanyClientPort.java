package com.sparta_logistics.order.application.port;

import com.sparta_logistics.order.infrastructure.client.dto.company.CompanyReadResponse;

public interface CompanyClientPort {

  CompanyReadResponse findCompanyInfoForCreateByUserId(String userId);

  String findCompanyAffiliationHubIdByUserId(String userId);

  String findCompanyNameByCompanyId(String companyId);
}
