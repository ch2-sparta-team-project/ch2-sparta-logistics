package com.sparta_logistics.delivery.presentation.controller;

import com.sparta_logistics.delivery.application.dto.DeliveryManagerResponse;
import com.sparta_logistics.delivery.application.service.DeliveryManagerService;
import com.sparta_logistics.delivery.presentation.dto.auth.RequestUserDetails;
import com.sparta_logistics.delivery.presentation.dto.request.DeliveryManagerCreateRequest;
import com.sparta_logistics.delivery.presentation.dto.request.DeliveryManagerUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  /**
   * DeliveryManager 수정 hubId 혹은 status 변경 가능 null 로 전달될 경우 변경되지 않음
   */
  @Secured({"ROLE_MASTER", "ROLE_HUB_MANAGER"})
  @PatchMapping("/{id}")
  public ResponseEntity<DeliveryManagerResponse> updateDeliveryManager(
      @PathVariable("id") String deliverManagerId,
      @RequestBody @Valid DeliveryManagerUpdateRequest request,
      @AuthenticationPrincipal RequestUserDetails user
  ) {
    return ResponseEntity.ok(
        deliveryManagerService.update(deliverManagerId, request.toDto(), user.getUserId(),
            user.getRole()));
  }


  /**
   * DeliveryManager 삭제 (Soft Delete)
   */
  @Secured({"ROLE_MASTER", "ROLE_HUB_MANAGER"})
  @DeleteMapping("/{id}")
  public ResponseEntity<DeliveryManagerResponse> deleteDeliveryManager(
      @PathVariable("id") String id,
      @AuthenticationPrincipal RequestUserDetails user
  ) {
    return ResponseEntity.ok(deliveryManagerService.delete(id, user.getUserId(), user.getRole()));
  }
}