package com.sparta_logistics.order.infrastructure.client;

import com.sparta_logistics.order.infrastructure.client.dto.delivery.DeliveryCreateRequest;
import com.sparta_logistics.order.infrastructure.client.dto.delivery.DeliveryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "delivery-service")
public interface DeliveryClient {

  @PostMapping("/api/v1/deliveries")
  ResponseEntity<DeliveryResponse> createDelivery(
      @RequestBody DeliveryCreateRequest request,
      @RequestHeader("X-User-Role") String userRole,
      @RequestHeader("X-User-Id") String userId,
      @RequestHeader("X-User-Name") String userName
  );

}
