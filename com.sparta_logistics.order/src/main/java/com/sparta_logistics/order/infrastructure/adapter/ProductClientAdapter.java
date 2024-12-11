package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.application.port.ProductClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductClientAdapter implements ProductClientPort {

  @Override
  public String findHubIdByProductId(String productId) {
    return "mock_hub_id" + UUID.randomUUID();
  }

  @Override
  public void updateProductQuantity(int purchaseQuantity) {
    //구매한 만큼 수량 감소
  }
}
