package com.sparta_logistics.product.application.service;

import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.domain.repository.ProductRepository;
import com.sparta_logistics.product.presentation.dto.CreateProductRequest;
import com.sparta_logistics.product.presentation.dto.CreateProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public CreateProductResponse createProduct(CreateProductRequest request) {
    //COMPANY 유효성 체크

    //HUB 유효성 체크

    Product product = productRepository.save(Product.fromCreateRequest(request));
    return CreateProductResponse.of(product.getId());
  }
}
