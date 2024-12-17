package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.infrastructure.client.CompanyClient;
import com.sparta_logistics.order.application.port.CompanyClientPort;
import com.sparta_logistics.order.infrastructure.client.dto.company.CompanyReadResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClientAdapter implements CompanyClientPort {

  private final CompanyClient companyClient;

  @Override
  public CompanyReadResponse findCompanyInfoForCreateByUserId(String userId) {
    return companyClient.searchCompanies(
            null, UUID.fromString(userId),
            Pageable.unpaged(),
            "ROLE_MASTER","85460f92-558c-4efa-84de-7607dfd941b3","123"
        ).getBody().getContent()
        .get(0);
  }

  @Override
  public String findCompanyAffiliationHubIdByUserId(String userId) {
    return companyClient.searchCompanies(
            null, UUID.fromString(userId),
            Pageable.unpaged(),
            "ROLE_MASTER","85460f92-558c-4efa-84de-7607dfd941b3","123"
        ).getBody().getContent()
        .get(0)
        .getHubId().toString();
  }

  @Override
  public String findCompanyNameByCompanyId(String companyId) {
    return companyClient.readCompany(UUID.fromString(companyId),"ROLE_MASTER","85460f92-558c-4efa-84de-7607dfd941b3","123")
        .getBody()
        .getName();
  }
}
