package com.sparta_logistics.delivery.infrastructure.adapter;

import com.sparta_logistics.delivery.application.port.CompanyClientPort;
import com.sparta_logistics.delivery.infrastructure.client.CompanyClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClientAdapter implements CompanyClientPort {

  private final CompanyClient companyClient;

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

}
