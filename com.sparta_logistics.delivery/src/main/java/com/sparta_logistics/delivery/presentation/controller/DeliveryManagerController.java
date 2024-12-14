package com.sparta_logistics.delivery.presentation.controller;

import com.sparta_logistics.delivery.application.dto.DeliveryManagerResponse;
import com.sparta_logistics.delivery.application.service.DeliveryManagerService;
import com.sparta_logistics.delivery.presentation.dto.RequestUserDetails;
import com.sparta_logistics.delivery.presentation.request.DeliveryManagerCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-managers")
public class DeliveryManagerController {

  private final DeliveryManagerService deliveryManagerService;

  /**
   * DeliveryManager 생성
   */
  @Secured({"ROLE_MASTER", "ROLE_HUB_MANAGER"})
  @PostMapping("")
  public ResponseEntity<DeliveryManagerResponse> createDeliveryManager(
      @RequestBody @Valid DeliveryManagerCreateRequest request,
      @AuthenticationPrincipal RequestUserDetails user
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(deliveryManagerService.create(request.toDto(), user.getUserId(), user.getRole()));
  }
}