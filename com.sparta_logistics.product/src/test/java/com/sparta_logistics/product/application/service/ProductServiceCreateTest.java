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
import com.sparta_logistics.product.presentation.dto.ProductCreateRequest;
import com.sparta_logistics.product.presentation.dto.ProductCreateResponse;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceCreateTest {

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
  @DisplayName("상품 생성 성공1 : HUB_MANAGER")
  void product_create_success_hub_manager() {
    // given
    UUID userId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    String role = "HUB_MANAGER";
    UUID hubId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();
    UUID companyManagerId = UUID.randomUUID();

    ProductCreateRequest request = new ProductCreateRequest(
        companyId,
        hubId,
        "product",
        100,
        "iamge",
        10000L
    );

    HubDto hubDto = new HubDto(hubId, userId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(any())).thenReturn(hubDto);

    CompanyDto companyDto = new CompanyDto(
        companyId,
        companyManagerId,
        hubId,
        "company",
        "address",
        "phone"
    );
    Mockito.when(companyFeignClient.readCompany(any())).thenReturn(companyDto);

    Product product = Product.createTestProduct(
        productId,
        companyId, hubId,
        "product",
        100,
        "image",
        10000L);
    Mockito.when(productRepository.save(any())).thenReturn(product);

    // when
    ProductCreateResponse response = productService.createProduct(userId, role, request);

    // then
    assertEquals(product.getId(), response.getProductId());
    Mockito.verify(productRepository, Mockito.times(1)).save(any());
  }

  @Test
  @DisplayName("상품 생성 실패1 : HUB_MANAGER 이지만 담당 허브가 아닐 때")
  void product_create_fail_hub() {
    // given
    UUID userId = UUID.randomUUID();
    String role = "HUB_MANAGER";
    UUID hubId = UUID.randomUUID();
    UUID invalidHubManagerId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();

    ProductCreateRequest request = new ProductCreateRequest(
        companyId,
        hubId,
        "product",
        100,
        "iamge",
        10000L
    );

    HubDto hubDto = new HubDto(hubId, invalidHubManagerId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(any())).thenReturn(hubDto);

    // when & then
    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> productService.createProduct(userId,role, request));
    assertEquals(ErrorCode.FORBIDDEN_EXCEPTION, exception.getErrorCode());

    Mockito.verify(productRepository, Mockito.never()).save(any());
  }

  @Test
  @DisplayName("상품 생성 성공2 : COMPANY_MANAGER")
  void product_create_success2_company_manager() {
    // given
    UUID userId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    String role = "COMPANY_MANAGER";
    UUID hubId = UUID.randomUUID();
    UUID hubManagerId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();

    ProductCreateRequest request = new ProductCreateRequest(
        companyId,
        hubId,
        "product",
        100,
        "iamge",
        10000L
    );

    HubDto hubDto = new HubDto(hubId, hubManagerId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(any())).thenReturn(hubDto);

    CompanyDto companyDto = new CompanyDto(
        companyId,
        userId,
        hubId,
        "company",
        "address",
        "phone"
    );
    Mockito.when(companyFeignClient.readCompany(any())).thenReturn(companyDto);

    Product product = Product.createTestProduct(
        productId,
        companyId, hubId,
        "product",
        100,
        "image",
        10000L);
    Mockito.when(productRepository.save(any())).thenReturn(product);

    // when
    ProductCreateResponse response = productService.createProduct(userId, role, request);

    // then
    assertEquals(product.getId(), response.getProductId());
    Mockito.verify(productRepository, Mockito.times(1)).save(any());
  }


  @Test
  @DisplayName("상품 생성 실패2 : COMPANY_MANAGER이지만 소속 허브 업체가 아닐 때")
  void product_create_fail2_company_manager() {
    // given
    UUID userId = UUID.randomUUID();
    String role = "COMPANY_MANAGER";
    UUID hubId = UUID.randomUUID();
    UUID hubManagerId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();
    UUID invalidHubId = UUID.randomUUID();

    ProductCreateRequest request = new ProductCreateRequest(
        companyId,
        hubId,
        "product",
        100,
        "iamge",
        10000L
    );

    HubDto hubDto = new HubDto(hubId, hubManagerId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(any())).thenReturn(hubDto);

    CompanyDto companyDto = new CompanyDto(
        companyId,
        userId,
        invalidHubId,
        "company",
        "address",
        "phone"
    );
    Mockito.when(companyFeignClient.readCompany(any())).thenReturn(companyDto);

    // when & then
    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> productService.createProduct(userId,role, request));
    assertEquals(ErrorCode.FORBIDDEN_EXCEPTION, exception.getErrorCode());

    Mockito.verify(productRepository, Mockito.never()).save(any());
  }

  @Test
  @DisplayName("상품 생성 실패3 : COMPANY_MANAGER이지만 해당 업체 MANAGER가 아닐 때")
  void product_create_fail3_company_manager() {
    // given
    UUID userId = UUID.randomUUID();
    String role = "COMPANY_MANAGER";
    UUID hubId = UUID.randomUUID();
    UUID hubManagerId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();
    UUID invalidCompanyManger = UUID.randomUUID();

    ProductCreateRequest request = new ProductCreateRequest(
        companyId,
        hubId,
        "product",
        100,
        "iamge",
        10000L
    );

    HubDto hubDto = new HubDto(hubId, hubManagerId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(any())).thenReturn(hubDto);

    CompanyDto companyDto = new CompanyDto(
        companyId,
        invalidCompanyManger,
        hubId,
        "company",
        "address",
        "phone"
    );
    Mockito.when(companyFeignClient.readCompany(any())).thenReturn(companyDto);

    // when & then
    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> productService.createProduct(userId,role, request));
    assertEquals(ErrorCode.FORBIDDEN_EXCEPTION, exception.getErrorCode());

    Mockito.verify(productRepository, Mockito.never()).save(any());
  }
}
