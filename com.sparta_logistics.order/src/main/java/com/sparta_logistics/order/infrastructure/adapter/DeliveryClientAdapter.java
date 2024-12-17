package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.infrastructure.client.dto.delivery.DeliveryCreateRequest;
import com.sparta_logistics.order.application.port.DeliveryClientPort;
import com.sparta_logistics.order.infrastructure.client.DeliveryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryClientAdapter implements DeliveryClientPort {

  private final DeliveryClient deliveryClient;

  @Override
  public String createDelivery(DeliveryCreateRequest deliveryCreateRequest) {
    return deliveryClient.createDelivery(deliveryCreateRequest
        ,"ROLE_MASTER","3a03bfe9-1fc5-4cdc-816d-9acd8d9b6ec8","r")
        .getBody()
        .deliveryId();
  }

  @Override
  public void deleteDeliveryById(String deliveryId) {

  }
}
