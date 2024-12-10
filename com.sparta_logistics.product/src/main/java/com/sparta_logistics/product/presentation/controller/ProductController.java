package com.sparta_logistics.product.presentation.controller;

import com.sparta_logistics.product.presentation.dto.CreateProductRequest;
import com.sparta_logistics.product.presentation.dto.CreateProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<CreateProductResponse> createProduct(
      @RequestBody CreateProductRequest request) {

    CreateProductResponse response = productService.createProduct(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
