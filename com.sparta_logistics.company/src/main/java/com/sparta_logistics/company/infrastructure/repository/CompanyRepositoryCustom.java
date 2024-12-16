package com.sparta_logistics.company.infrastructure.repository;

import com.sparta_logistics.company.application.dto.CompanyReadResponse;
import com.sparta_logistics.company.presentation.request.CompanySearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

  Page<CompanyReadResponse> searchCompanies(CompanySearchRequest companySearchRequest,Pageable pageable);

}
