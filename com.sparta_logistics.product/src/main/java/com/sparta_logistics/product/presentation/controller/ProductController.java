package com.sparta_logistics.product.presentation.controller;

import com.sparta_logistics.product.application.service.ProductService;
import com.sparta_logistics.product.presentation.dto.CreateProductRequest;
import com.sparta_logistics.product.presentation.dto.CreateProductResponse;
import com.sparta_logistics.product.presentation.dto.ReadProductResponse;
import com.sparta_logistics.product.presentation.dto.UpdateProductRequest;
import com.sparta_logistics.product.presentation.dto.UpdateProductResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<CreateProductResponse> createProduct(
      @RequestBody CreateProductRequest request) {

    CreateProductResponse response = productService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ReadProductResponse> readProduct(@PathVariable UUID productId) {
    ReadProductResponse response = productService.readProduct(productId);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<ReadProductResponse>> readProducts() {
    List<ReadProductResponse> responses = productService.readProducts();
    return ResponseEntity.ok(responses);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<UpdateProductResponse> updateProduct(@PathVariable UUID productId,
      @RequestBody UpdateProductRequest request) {
    UpdateProductResponse response = productService.updateProduct(productId, request);
    return ResponseEntity.ok(response);
  }
}