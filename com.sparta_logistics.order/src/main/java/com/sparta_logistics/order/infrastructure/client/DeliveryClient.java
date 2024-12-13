package com.sparta_logistics.order.infrastructure.client;

import com.sparta_logistics.order.infrastructure.client.dto.DeliveryCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface DeliveryClient {
  ResponseEntity<?> createDelivery(DeliveryCreateRequest deliveryCreateRequest);

}
