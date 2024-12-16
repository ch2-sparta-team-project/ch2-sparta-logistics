package com.sparta_logistics.company.application.service;

import com.sparta_logistics.company.application.client.HubClient;
import com.sparta_logistics.company.application.dto.CompanyCreateResponse;
import com.sparta_logistics.company.application.dto.CompanyReadResponse;
import com.sparta_logistics.company.domain.model.Company;
import com.sparta_logistics.company.global.exception.ApplicationException;
import com.sparta_logistics.company.global.exception.ErrorCode;
import com.sparta_logistics.company.infrastructure.repository.CompanyRepository;
import com.sparta_logistics.company.presentation.request.CompanyCreateRequest;
import com.sparta_logistics.company.presentation.request.CompanySearchRequest;
import com.sparta_logistics.company.presentation.request.CompanyUpdateRequest;
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

  public CompanyReadResponse readCompany(UUID companyId) {
    Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId);
    return CompanyReadResponse.of(company);
  }

  public String updateCompany(CompanyUpdateRequest req, UUID companyId) {
    Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId);
    company.update(req);
    return "업체 정보가 업데이트되었습니다.";
  }

  public String deleteCompany(UUID companyId, String userId) {
    Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId);
    company.delete(userId);
    return "업체가 삭제되었습니다.";
  }

  public CompanyReadResponse restoreCompany(UUID companyId) {
    Company company = companyRepository.findById(companyId).orElseThrow(
        () -> new ApplicationException(ErrorCode.INVALID_VALUE_EXCEPTION)
    );
    company.restore();
    return CompanyReadResponse.of(company);
  }
}
