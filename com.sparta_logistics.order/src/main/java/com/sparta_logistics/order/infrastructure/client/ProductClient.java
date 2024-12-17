package com.sparta_logistics.order.infrastructure.client;

import com.sparta_logistics.order.infrastructure.client.dto.product.ProductReadResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "product-service")
public interface ProductClient {

  @GetMapping("/api/v1/products/{productId}")
  ResponseEntity<ProductReadResponse> readProduct(
      @PathVariable UUID productId,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-User-Id") String userId,
      @RequestHeader("X-User-Name") String userName
  );


}
