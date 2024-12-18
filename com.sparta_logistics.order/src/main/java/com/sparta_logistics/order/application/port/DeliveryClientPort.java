package com.sparta_logistics.order.application.port;


import com.sparta_logistics.order.infrastructure.client.dto.delivery.DeliveryCreateRequest;

public interface DeliveryClientPort {

  String createDelivery(DeliveryCreateRequest deliveryCreateRequest);

  void deleteDeliveryById(String deliveryId);
}
