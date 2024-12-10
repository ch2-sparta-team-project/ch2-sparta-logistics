package com.sparta_logistics.order.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "delivery")
public interface DeliveryClient {

  @PostMapping("/api/v1/deliveries")
  ResponseEntity<?> createDelivery();

}
