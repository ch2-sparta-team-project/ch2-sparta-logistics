package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.infrastructure.client.dto.DeliveryCreateRequest;
import com.sparta_logistics.order.application.port.DeliveryClientPort;
import com.sparta_logistics.order.infrastructure.client.DeliveryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryClientAdapter implements DeliveryClientPort {

  @Override
  public String createDelivery(DeliveryCreateRequest deliveryCreateRequest) {

    return "mock_delivery_id" + UUID.randomUUID();
    //return response.getBody();
  }
}
