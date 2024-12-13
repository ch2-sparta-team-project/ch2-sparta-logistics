package com.sparta_logistics.product.domain.repository;

import com.sparta_logistics.product.domain.model.Product;
import com.sparta_logistics.product.presentation.dto.ProductReadResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
  Page<ProductReadResponse> findAll(List<UUID> ids, String name, Boolean outOfStock, Long minPrice, Long maxPrice, Pageable pageable);
}
