package com.sparta_logistics.order.application.port;

public interface ProductClientPort {

  String findHubIdByProductId(String productId);

  void updateProductQuantity(int purchaseQuantity);
}
