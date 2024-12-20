package com.sparta_logistics.product.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.infrastructure.repository.ProductRepository;
import com.sparta_logistics.product.global.exception.ApplicationException;
import com.sparta_logistics.product.global.exception.ErrorCode;
import com.sparta_logistics.product.infrastructure.client.CompanyFeignClient;
import com.sparta_logistics.product.infrastructure.client.HubFeignClient;
import com.sparta_logistics.product.infrastructure.dto.CompanyDto;
import com.sparta_logistics.product.infrastructure.dto.HubDto;
import com.sparta_logistics.product.presentation.dto.ProductUpdateRequest;
import com.sparta_logistics.product.presentation.dto.ProductUpdateResponse;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ProductServiceUpdateTest {

  @Mock
  private HubFeignClient hubFeignClient;

  @Mock
  private CompanyFeignClient companyFeignClient;

  @Mock
  private ProductRepository productRepository;

  private ProductService productService;

  @BeforeEach
  public void beforeEach() {
    this.productService = new ProductService(productRepository, companyFeignClient, hubFeignClient);
  }

  @Test
  @DisplayName("상품 수정 성공 : HUB_MANAGER")
  void product_update_success_hubManager() {
    // given
    UUID userId = UUID.randomUUID();
    String role = "HUB_MANAGER";
    UUID productId = UUID.randomUUID();
    UUID hubId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();

    Product product = Product.createTestProduct(
        productId, companyId, hubId, "product", 100, "image", 10000L);
    Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    HubDto hubDto = new HubDto(hubId, userId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(any())).thenReturn(hubDto);

    ProductUpdateRequest request = new ProductUpdateRequest(
        "updated product", 200, "updated image", 20000L);

    // when
    ProductUpdateResponse response = productService.updateProduct(userId, role, productId, request);

    // then
    assertEquals(product.getId(), response.getProductId());
    assertEquals("updated product", product.getName());
    assertEquals(200, product.getStock());
    assertEquals("updated image", product.getImageUrl());
    assertEquals(20000L, product.getPrice());
    Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
  }

  @Test
  @DisplayName("상품 수정 실패 : HUB_MANAGER 이지만 해당 허브 관리자가 아님")
  void product_update_fail_hubManager() {
    // given
    UUID userId = UUID.randomUUID();
    String role = "HUB_MANAGER";
    UUID productId = UUID.randomUUID();
    UUID hubId = UUID.randomUUID();
    UUID invalidManagerId = UUID.randomUUID();

    Product product = Product.createTestProduct(
        productId, UUID.randomUUID(), hubId, "product", 100, "image", 10000L);
    Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    HubDto hubDto = new HubDto(hubId, invalidManagerId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(any())).thenReturn(hubDto);

    ProductUpdateRequest request = new ProductUpdateRequest(
        "updated product", 200, "updated image", 20000L);

    // when & then
    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> productService.updateProduct(userId, role, productId, request));
    assertEquals(ErrorCode.FORBIDDEN_EXCEPTION, exception.getErrorCode());

    Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
  }

  @Test
  @DisplayName("상품 수정 성공 : COMPANY_MANAGER")
  void product_update_success_companyManager() {
    // given
    UUID userId = UUID.randomUUID();
    String role = "COMPANY_MANAGER";
    UUID productId = UUID.randomUUID();
    UUID hubId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();

    Product product = Product.createTestProduct(
        productId, companyId, hubId, "product", 100, "image", 10000L);
    Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    CompanyDto companyDto = new CompanyDto(companyId, userId, hubId, "company", "address", "phone");
    Mockito.when(companyFeignClient.readCompany(any())).thenReturn(companyDto);

    ProductUpdateRequest request = new ProductUpdateRequest(
        "updated product", 200, "updated image", 20000L);

    // when
    ProductUpdateResponse response = productService.updateProduct(userId, role, productId, request);

    // then
    assertEquals(product.getId(), response.getProductId());
    assertEquals("updated product", product.getName());
    assertEquals(200, product.getStock());
    assertEquals("updated image", product.getImageUrl());
    assertEquals(20000L, product.getPrice());
    Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
  }

  @Test
  @DisplayName("상품 수정 실패 : COMPANY_MANAGER 이지만 다른 회사 소속일 때")
  void product_update_fail_companyManager_wrongCompany() {
    // given
    UUID userId = UUID.randomUUID();
    String role = "COMPANY_MANAGER";
    UUID productId = UUID.randomUUID();
    UUID hubId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();
    UUID invalidCompanyManagerId = UUID.randomUUID();

    Product product = Product.createTestProduct(
        productId, companyId, hubId, "product", 100, "image", 10000L);
    Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    CompanyDto companyDto = new CompanyDto(
        companyId,
        invalidCompanyManagerId,
        hubId,
        "company",
        "address",
        "phone"
    );
    Mockito.when(companyFeignClient.readCompany(any())).thenReturn(companyDto);

    ProductUpdateRequest request = new ProductUpdateRequest(
        "updated product", 200, "updated image", 20000L);

    // when & then
    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> productService.updateProduct(userId, role, productId, request));
    assertEquals(ErrorCode.FORBIDDEN_EXCEPTION, exception.getErrorCode());

    Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
  }

  @Test
  @DisplayName("상품 수정 실패 : 상품이 존재하지 않을 때")
  void product_update_fail_notFound() {
    // given
    UUID userId = UUID.randomUUID();
    String role = "HUB_MANAGER";
    UUID productId = UUID.randomUUID();

    // Mock 설정: 상품이 존재하지 않음
    Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

    ProductUpdateRequest request = new ProductUpdateRequest(
        "updated product", 200, "updated image", 20000L);

    // when & then
    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> productService.updateProduct(userId, role, productId, request));
    assertEquals(ErrorCode.NOT_FOUND_EXCEPTION, exception.getErrorCode());

    // repository save 호출이 발생하지 않아야 함
    Mockito.verify(productRepository, Mockito.never()).save(any());
  }
}
