package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.infrastructure.client.CompanyClient;
import com.sparta_logistics.order.infrastructure.client.dto.CompanyCreateDto;
import com.sparta_logistics.order.application.port.CompanyClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClientAdapter implements CompanyClientPort {

  @Override
  public CompanyCreateDto findCompanyInfoForCreateByUserId(String userId) {
    return new CompanyCreateDto("mock_company_id", "mock_hub_id", "address", 111.1, 111.1);
  }

  @Override
  public String findCompanyAffiliationHubIdByUserId(String userId) {
    return "mock_affiliation_hub_id";
  }

  @Override
  public String findCompanyNameByCompanyId(String companyId) {
    return "mock_company_name";
  }
}
