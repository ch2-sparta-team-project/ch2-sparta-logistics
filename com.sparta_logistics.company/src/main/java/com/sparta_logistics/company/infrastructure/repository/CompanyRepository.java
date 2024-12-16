package com.sparta_logistics.company.infrastructure.repository;

import com.sparta_logistics.company.domain.model.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID>, CompanyRepositoryCustom {
  Company findByIdAndDeletedAtIsNull(UUID companyId);
}
