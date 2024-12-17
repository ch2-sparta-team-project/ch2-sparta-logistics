package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.application.port.ProductClientPort;
import com.sparta_logistics.order.infrastructure.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductClientAdapter implements ProductClientPort {

  private final ProductClient productClient;

  @Override
  public String findHubIdByProductId(String productId) {
    return productClient.readProduct(UUID.fromString(productId), "MASTER","123","123")
        .getBody()
        .getHubId()
        .toString();
  }

  @Override
  public void updateProductQuantity(int purchaseQuantity) {
    //구매한 만큼 수량 감소
  }

  @Override
  public String findProductNameByProductId(String productId) {
    return productClient.readProduct(UUID.fromString(productId),"MASTER","123","123")
        .getBody()
        .getProductName();
  }
}
