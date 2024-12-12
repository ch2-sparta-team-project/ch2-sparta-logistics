package com.sparta_logistics.product.presentation.dto;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {
  List<UUID> ids;
  String name;
  Boolean outOfStock;
  Long minPrice;
  Long maxPrice;
}
