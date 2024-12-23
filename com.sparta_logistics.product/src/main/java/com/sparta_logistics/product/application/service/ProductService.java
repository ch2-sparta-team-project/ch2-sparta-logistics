package com.sparta_logistics.product.application.service;

import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.global.exception.ApplicationException;
import com.sparta_logistics.product.global.exception.ErrorCode;
import com.sparta_logistics.product.infrastructure.client.CompanyFeignClient;
import com.sparta_logistics.product.infrastructure.client.HubFeignClient;
import com.sparta_logistics.product.infrastructure.dto.CompanyDto;
import com.sparta_logistics.product.infrastructure.dto.HubDto;
import com.sparta_logistics.product.infrastructure.repository.ProductRepository;
import com.sparta_logistics.product.presentation.dto.ProductCreateRequest;
import com.sparta_logistics.product.presentation.dto.ProductCreateResponse;
import com.sparta_logistics.product.presentation.dto.ProductDeleteResponse;
import com.sparta_logistics.product.presentation.dto.ProductReadDetailResponse;
import com.sparta_logistics.product.presentation.dto.ProductReadResponse;
import com.sparta_logistics.product.presentation.dto.ProductSearchRequest;
import com.sparta_logistics.product.presentation.dto.ProductUpdateRequest;
import com.sparta_logistics.product.presentation.dto.ProductUpdateResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;
  private final CompanyFeignClient companyFeignClient;

  private final HubFeignClient hubFeignClient;

  @Transactional
  public ProductCreateResponse createProduct(
      UUID userId,
      String role,
      ProductCreateRequest request
  ) {
    HubDto hubDto = hubFeignClient.readHub(request.getHubId());
    CompanyDto companyDto = companyFeignClient.readCompany(request.getCompanyId());

    // 비즈니스 로직
    if (role.equals("HUB_MANAGER")) {
      isValidManager(userId, hubDto.getUserId());
    } else if (role.equals("COMPANY_MANAGER")) {
      isValidManager(userId, companyDto.getUserId());
      isValidCompanyByHub(request.getHubId(), companyDto.getHubId());
    }

    Product product = productRepository.save(
        Product.create(
            request.getCompanyId(),
            request.getHubId(),
            request.getName(),
            request.getStock(),
            request.getImageUrl(),
            request.getPrice())
    );
    return ProductCreateResponse.of(product.getId());
  }

  public ProductReadDetailResponse readProduct(UUID productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    HubDto hubDto = hubFeignClient.readHub(product.getHubId());
    CompanyDto companyDto = companyFeignClient.readCompany(product.getCompanyId());

    return ProductReadDetailResponse.of(product, companyDto, hubDto);
  }

  public Page<ProductReadResponse> readProducts(
      ProductSearchRequest request,
      Pageable pageable
  ) {
    return productRepository.findAll(
        request.getIds(),
        request.getName(),
        request.getOutOfStock(),
        request.getMinPrice(),
        request.getMaxPrice(),
        pageable);
  }

  @Transactional
  public ProductUpdateResponse updateProduct(
      UUID userId,
      String role,
      UUID productId,
      ProductUpdateRequest request
  ) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    // 비즈니스 로직
    if (role.equals("HUB_MANAGER")) {
      HubDto hubDto = hubFeignClient.readHub(product.getHubId());
      isValidManager(userId, hubDto.getUserId());
    } else if (role.equals("COMPANY_MANAGER")) {
      CompanyDto companyDto = companyFeignClient.readCompany(product.getCompanyId());
      isValidManager(userId, companyDto.getUserId());
    }

    product.updateProductUsingRequest(request);
    return ProductUpdateResponse.of(product.getId());
  }

  @Transactional
  public ProductDeleteResponse deleteProduct(
      UUID userId,
      String username,
      String role,
      UUID productId
  ) {
    //삭제된 상품에 대한 주문은 어떻게 처리할지 고민 필요

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    if (role.equals("HUB_MANAGER")) {
      HubDto hubDto = hubFeignClient.readHub(product.getHubId());
      isValidManager(userId, hubDto.getUserId());
    }

    product.delete(username);
    return ProductDeleteResponse.of(product.getId());
  }

  private PagedModel<ProductReadResponse> toPagedModel(Page<ProductReadResponse> page) {
    PagedModel.PageMetadata metadata = new PageMetadata(
        page.getSize(),
        page.getNumber(),
        page.getTotalElements(),
        page.getTotalPages()
    );
    return PagedModel.of(page.getContent(), metadata);
  }

  private void isValidManager(UUID userId, UUID managerId) {
    if (!userId.equals(managerId)) {
      throw new ApplicationException(ErrorCode.FORBIDDEN_EXCEPTION);
    }
  }

  private void isValidCompanyByHub(UUID hubId, UUID companyHubId) {
    if (!hubId.equals(companyHubId)) {
      throw new ApplicationException(ErrorCode.FORBIDDEN_EXCEPTION);
    }
  }
}
