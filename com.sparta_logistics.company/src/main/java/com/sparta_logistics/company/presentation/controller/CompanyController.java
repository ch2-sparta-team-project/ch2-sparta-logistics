package com.sparta_logistics.company.presentation.controller;

import com.sparta_logistics.company.application.dto.CompanyCreateResponse;
import com.sparta_logistics.company.application.dto.CompanyReadResponse;
import com.sparta_logistics.company.application.service.CompanyService;
import com.sparta_logistics.company.presentation.dto.RequestUserDetails;
import com.sparta_logistics.company.presentation.request.CompanyCreateRequest;
import com.sparta_logistics.company.presentation.request.CompanySearchRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

  private final CompanyService companyService;

  // 업체 생성
  @Secured({"ROLE_MASTER", "ROLE_COMPANY_MANAGER", "MASTER"})
  @PostMapping
  public ResponseEntity<CompanyCreateResponse> createCompany(
      @RequestBody @Valid CompanyCreateRequest request,
      @AuthenticationPrincipal RequestUserDetails userDetails) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(companyService.createCompany(request, userDetails.getUserId(), userDetails.getUsername()));
  }

//   업체 목록 조회
  // 전체 권한
  @GetMapping
  public ResponseEntity<Page<CompanyReadResponse>> searchCompanies(
      CompanySearchRequest request,
      @PageableDefault Pageable pageable
  ) {
    return ResponseEntity.ok(companyService.searchCompanies(request, pageable));
  }

  // 업체 단일 조회
  // 전체 권한
  @GetMapping("{companyId}")
  public ResponseEntity<CompanyReadResponse> readCompany(
      @PathVariable("companyId") UUID companyId) {
    return ResponseEntity.ok(companyService.readCompany(companyId));
  }
//
//  // 업체 수정
//  @Secured({"ROLE_MASTER", "ROLE_COMPANY_MANAGER"})
//  @PutMapping("{companyId}")
//  public ResponseEntity<String> updateCompany(
//      @PathVariable("companyId") String companyId,
//      @RequestBody @Valid CompanyUpdateRequest req,
//      @AuthenticationPrincipal RequestUserDetails user) {
//    return ResponseEntity.ok(
//        companyService.updateCompany(req, companyId, user.getUserId(), user.getRole()));
//  }
//
//  // 업체 삭제
//  @Secured({"ROLE_MASTER", "ROLE_COMPANY_MANAGER"})
//  @DeleteMapping("{companyId}")
//  public ResponseEntity<String> deleteOrder(
//      @PathVariable("companyId") String companyId,
//      @AuthenticationPrincipal RequestUserDetails user) {
//    return ResponseEntity.ok(companyService.deleteOrder(companyId, user.getUserId(), user.getRole()));
//  }
}
