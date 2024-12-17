package com.sparta_logistics.product.application.service;

import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.domain.repository.ProductRepository;
import com.sparta_logistics.product.global.exception.ApplicationException;
import com.sparta_logistics.product.global.exception.ErrorCode;
import com.sparta_logistics.product.infrastructure.client.CompanyFeignClient;
import com.sparta_logistics.product.infrastructure.client.HubFeignClient;
import com.sparta_logistics.product.infrastructure.dto.HubDto;
import com.sparta_logistics.product.presentation.dto.ProductCreateRequest;
import com.sparta_logistics.product.presentation.dto.ProductCreateResponse;
import com.sparta_logistics.product.presentation.dto.ProductDeleteResponse;
import com.sparta_logistics.product.presentation.dto.ProductReadResponse;
import com.sparta_logistics.product.presentation.dto.ProductSearchRequest;
import com.sparta_logistics.product.presentation.dto.ProductUpdateRequest;
import com.sparta_logistics.product.presentation.dto.ProductUpdateResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //HUB 유효성 체크
    HubDto hubResponse = hubFeignClient.readHub(request.getHubId());

    //COMPANY 유효성 체크
    //CompanyDto companyResponse = companyFeignClient.readCompany(request.getCompanyId());

    //업체는 소속 허브에서만 상품 등록이 가능한지 확인 필요

    if (role.equals("ROLE_HUB_MANAGER")) {
      //HUB_MANAGER 담당 허브인지 유효성 체크
    } else if (role.equals("ROLE_COMPANY_MANAGER")) {
      //COMPANY_MANAGER 담당 업체인지 유효성 체크
    }

    Product product = productRepository.save(Product.fromCreateRequest(request));
    return ProductCreateResponse.of(product.getId());
  }


  public ProductReadResponse readProduct(UUID productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    return ProductReadResponse.of(product);
  }

  public PagedModel<ProductReadResponse> readProducts(
      ProductSearchRequest request,
      Pageable pageable
  ) {
    return toPagedModel(productRepository.findAll(
        request.getIds(),
        request.getName(),
        request.getOutOfStock(),
        request.getMinPrice(),
        request.getMaxPrice(),
        pageable));
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

    if (role.equals("ROLE_HUB_MANAGER")) {
      HubDto hub = hubFeignClient.readHub(product.getHubId());
      //isValidManager(userId, hub.getUserId());
    } else if (role.equals("ROLE_COMPANY_MANAGER")) {
      //CompanyDto company = companyFeignClient.readCompany(product.getCompanyId());
      //isValidManager(userId, company.getUserId());
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

    if (role.equals("ROLE_HUB_MANAGER")) {
      HubDto hub = hubFeignClient.readHub(product.getHubId());
      //isValidManager(userId, hub.getUserId());
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
}
