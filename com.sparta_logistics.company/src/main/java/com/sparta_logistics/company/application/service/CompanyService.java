package com.sparta_logistics.company.application.service;

import com.sparta_logistics.company.application.client.HubClient;
import com.sparta_logistics.company.application.dto.CompanyCreateResponse;
import com.sparta_logistics.company.application.dto.CompanyReadResponse;
import com.sparta_logistics.company.domain.model.Company;
import com.sparta_logistics.company.infrastructure.repository.CompanyRepository;
import com.sparta_logistics.company.presentation.request.CompanyCreateRequest;
import com.sparta_logistics.company.presentation.request.CompanySearchRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final HubClient hubClient;

  public CompanyCreateResponse createCompany(CompanyCreateRequest request, String userId, String username) {
    Company company = companyRepository.save(
        Company.createCompany(UUID.fromString(userId),
            request.hubId(),
            request.name(),
            request.address(),
            request.companyType(),
            request.latitude(),
            request.longitude(),
            request.phone(),
            username));
    return new CompanyCreateResponse(company);
  }

  public Page<CompanyReadResponse> searchCompanies(CompanySearchRequest request, Pageable pageable) {
    return companyRepository.searchCompanies(request, pageable);
  }
}
