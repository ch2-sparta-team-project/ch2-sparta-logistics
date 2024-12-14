package com.sparta_logistics.delivery.infrastructure.adapter;

import com.sparta_logistics.delivery.application.port.CompanyClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClientAdapter implements CompanyClientPort {
  @Override
  public String findCompanyAffiliationHubIdByUserId(String userId) {
    return "mock_affiliation_hub_id";
  }

}
