package com.sparta_logistics.product.application.service;

import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.domain.repository.ProductRepository;
import com.sparta_logistics.product.global.exception.ApplicationException;
import com.sparta_logistics.product.global.exception.ErrorCode;
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

  @Transactional
  public ProductCreateResponse createProduct(ProductCreateRequest request) {
    //유저 유효성 체크 (HUB_MANAGER, COMPANY_MANAGER)

    //COMPANY 유효성 체크

    //HUB 유효성 체크

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
        pageable)
    );
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

  @Transactional
  public ProductUpdateResponse updateProduct(UUID productId, ProductUpdateRequest request) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    product.updateProductUsingRequest(request);

    return ProductUpdateResponse.of(product.getId());
  }

  @Transactional
  public ProductDeleteResponse deleteProduct(UUID productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    product.delete();

    return ProductDeleteResponse.of(product.getId());
  }
}
