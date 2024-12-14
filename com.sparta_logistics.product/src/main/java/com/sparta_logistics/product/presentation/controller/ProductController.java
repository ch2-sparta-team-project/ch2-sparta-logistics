package com.sparta_logistics.product.presentation.controller;

import com.sparta_logistics.product.application.service.ProductService;
import com.sparta_logistics.product.presentation.dto.ProductCreateRequest;
import com.sparta_logistics.product.presentation.dto.ProductCreateResponse;
import com.sparta_logistics.product.presentation.dto.ProductDeleteResponse;
import com.sparta_logistics.product.presentation.dto.ProductReadResponse;
import com.sparta_logistics.product.presentation.dto.ProductSearchRequest;
import com.sparta_logistics.product.presentation.dto.ProductUpdateRequest;
import com.sparta_logistics.product.presentation.dto.ProductUpdateResponse;
import com.sparta_logistics.product.presentation.dto.RequestUserDetails;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  @Secured({"MASTER", "HUB_MANAGER", "COMPANY_MANAGER"})
  public ResponseEntity<ProductCreateResponse> createProduct(
      @AuthenticationPrincipal RequestUserDetails user,
      @RequestBody ProductCreateRequest request
  ) {
    ProductCreateResponse response = productService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductReadResponse> readProduct(
      @PathVariable UUID productId,
      @AuthenticationPrincipal RequestUserDetails user
  ) {
    ProductReadResponse response = productService.readProduct(productId);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<PagedModel<ProductReadResponse>> readProducts(
      ProductSearchRequest request,
      @PageableDefault(size = 10, page = 0) Pageable pageable
  ) {
    PagedModel<ProductReadResponse> responses = productService
        .readProducts(request, pageable);
    return ResponseEntity.ok(responses);
  }

  @PutMapping("/{productId}")
  @Secured({"MASTER", "HUB_MANAGER", "COMPANY_MANAGER"})
  public ResponseEntity<ProductUpdateResponse> updateProduct(
      @PathVariable UUID productId,
      @RequestBody ProductUpdateRequest request
  ) {
    ProductUpdateResponse response = productService.updateProduct(productId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{productId}")
  @Secured({"MASTER", "HUB_MANAGER"})
  public ResponseEntity<ProductDeleteResponse> deleteProduct(
      @AuthenticationPrincipal RequestUserDetails user,
      @PathVariable UUID productId
  ) {
    ProductDeleteResponse response = productService.deleteProduct(productId);
    return ResponseEntity.ok(response);
  }
}
