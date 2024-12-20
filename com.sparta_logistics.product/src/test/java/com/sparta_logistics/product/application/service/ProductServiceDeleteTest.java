package com.sparta_logistics.product.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.infrastructure.repository.ProductRepository;
import com.sparta_logistics.product.global.exception.ApplicationException;
import com.sparta_logistics.product.global.exception.ErrorCode;
import com.sparta_logistics.product.infrastructure.client.HubFeignClient;
import com.sparta_logistics.product.infrastructure.dto.HubDto;
import com.sparta_logistics.product.presentation.dto.ProductDeleteResponse;
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
class ProductServiceDeleteTest {

  @Mock
  private HubFeignClient hubFeignClient;

  @Mock
  private ProductRepository productRepository;

  private ProductService productService;

  @BeforeEach
  public void beforeEach() {
    this.productService = new ProductService(productRepository, null, hubFeignClient);
  }

  @Test
  @DisplayName("상품 삭제 성공 : ROLE_HUB_MANAGER")
  void deleteProduct_success_hubManager() {
    // given
    UUID userId = UUID.randomUUID();
    String username = "user";
    String role = "HUB_MANAGER";
    UUID productId = UUID.randomUUID();
    UUID hubId = UUID.randomUUID();

    Product product = Product.createTestProduct(productId, UUID.randomUUID(), hubId, "product", 100, "image", 10000L);
    Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    HubDto hubDto = new HubDto(hubId, userId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(hubId)).thenReturn(hubDto);

    // when
    ProductDeleteResponse response = productService.deleteProduct(userId, username, role, productId);

    // then
    assertEquals(productId, response.getProductId());
    assertNotNull(product.getDeletedAt(), "DeletedAt should not be null after deletion.");
    assertEquals(username, product.getDeletedBy(), "DeletedBy should match the username used in deletion.");
  }

  @Test
  @DisplayName("상품 삭제 실패 : 상품이 존재하지 않을 때")
  void deleteProduct_fail_notFound() {
    // given
    UUID userId = UUID.randomUUID();
    String username = "user";
    String role = "HUB_MANAGER";
    UUID productId = UUID.randomUUID();

    Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

    // when & then
    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> productService.deleteProduct(userId, username, role, productId));
    assertEquals(ErrorCode.NOT_FOUND_EXCEPTION, exception.getErrorCode());

    Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
  }

  @Test
  @DisplayName("상품 삭제 실패 : HUB_MANAGER 소속 허브가 아닐 때")
  void deleteProduct_fail_invalidHubManager() {
    // given
    UUID userId = UUID.randomUUID();
    String username = "testUser";
    String role = "HUB_MANAGER";
    UUID productId = UUID.randomUUID();
    UUID hubId = UUID.randomUUID();
    UUID invalidManagerId = UUID.randomUUID();

    Product product = Product.createTestProduct(productId, UUID.randomUUID(), hubId, "product", 100, "image", 10000L);
    Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    HubDto hubDto = new HubDto(hubId, invalidManagerId, "hub", "address", false);
    Mockito.when(hubFeignClient.readHub(hubId)).thenReturn(hubDto);

    // when & then
    ApplicationException exception = assertThrows(ApplicationException.class,
        () -> productService.deleteProduct(userId, username, role, productId));
    assertEquals(ErrorCode.FORBIDDEN_EXCEPTION, exception.getErrorCode());

    Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
  }
}

