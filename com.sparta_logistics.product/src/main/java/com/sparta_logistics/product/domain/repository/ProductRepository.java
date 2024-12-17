package com.sparta_logistics.product.domain.repository;

import com.sparta_logistics.product.domain.model.Product;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, UUID>, ProductRepositoryCustom {
  @Query("SELECT p FROM Product p WHERE p.id = :productId AND p.deletedAt IS NULL")
  Optional<Product> findById(UUID productId);
}
