package com.sparta_logistics.product.domain.model;

import com.sparta_logistics.product.presentation.dto.CreateProductRequest;
import com.sparta_logistics.product.presentation.dto.UpdateProductRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "p_product")
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID companyId;

  @Column(nullable = false)
  private UUID hubId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer stock;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private Long price;

  public static Product fromCreateRequest(CreateProductRequest request) {

    return Product.builder()
        .companyId(request.getCompanyId())
        .hubId(request.getHubId())
        .name(request.getName())
        .stock(request.getStock())
        .imageUrl(request.getImageUrl())
        .price(request.getPrice())
        .build();
  }

  public void updateProductUsingRequest(UpdateProductRequest request) {
    this.name = request.getProductName();
    this.stock = request.getProductStock();
    this.imageUrl = request.getProductImageUrl();
    this.price = request.getProductPrice();
  }
}
