package com.sparta_logistics.product.application.service;

import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.domain.repository.ProductRepository;
import com.sparta_logistics.product.presentation.dto.CreateProductRequest;
import com.sparta_logistics.product.presentation.dto.CreateProductResponse;
import com.sparta_logistics.product.presentation.dto.ReadProductResponse;
import com.sparta_logistics.product.presentation.dto.UpdateProductRequest;
import com.sparta_logistics.product.presentation.dto.UpdateProductResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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

  public ReadProductResponse readProduct(UUID productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

    return ReadProductResponse.of(product);
  }

  public List<ReadProductResponse> readProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(ReadProductResponse::of).collect(Collectors.toList());
  }

  public UpdateProductResponse updateProduct(UUID productId, UpdateProductRequest request) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

    product.updateProductUsingRequest(request);
    productRepository.save(product);

    return UpdateProductResponse.of(product.getId());
  }
}
