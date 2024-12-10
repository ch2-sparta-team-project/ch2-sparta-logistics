package com.sparta_logistics.order.infrastructure.adapter;

import com.sparta_logistics.order.application.dto.CreateDeliveryReq;
import com.sparta_logistics.order.application.port.DeliveryClientPort;
import com.sparta_logistics.order.infrastructure.client.DeliveryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryClientAdapter implements DeliveryClientPort {

  private final DeliveryClient deliveryClient;

  @Override
  public String createDelivery(CreateDeliveryReq createDeliveryReq) {


    return "";
  }
}
