package com.sparta_logistics.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_product")
public class Product {

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

}
