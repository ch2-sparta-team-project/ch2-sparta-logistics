package com.sparta_logistics.product.domain.repository;

import com.sparta_logistics.product.domain.model.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
