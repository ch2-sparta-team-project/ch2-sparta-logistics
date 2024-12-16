package com.sparta_logistics.delivery.presentation.controller;

import com.sparta_logistics.delivery.application.dto.delivery.DeliveryResponse;
import com.sparta_logistics.delivery.application.service.DeliveryService;
import com.sparta_logistics.delivery.presentation.dto.auth.RequestUserDetails;
import com.sparta_logistics.delivery.presentation.dto.request.delivery.DeliveryCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

  private final DeliveryService deliveryService;

  /**
   * Delivery 생성
   */
  @PostMapping("")
  public ResponseEntity<DeliveryResponse> createDelivery(
      @RequestBody @Valid DeliveryCreateRequest request,
      @AuthenticationPrincipal RequestUserDetails user
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.create(request.toDto()));
  }

}
