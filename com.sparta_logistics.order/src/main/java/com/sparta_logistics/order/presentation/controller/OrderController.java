package com.sparta_logistics.order.presentation.controller;

import com.sparta_logistics.order.application.dto.OrderCreateResponse;
import com.sparta_logistics.order.application.dto.OrderDeleteResponse;
import com.sparta_logistics.order.application.dto.OrderReadAllResponse;
import com.sparta_logistics.order.application.dto.OrderReadResponse;
import com.sparta_logistics.order.application.dto.OrderUpdateResponse;
import com.sparta_logistics.order.application.service.OrderService;
import com.sparta_logistics.order.presentation.request.OrderUpdateRequest;
import com.sparta_logistics.order.presentation.dto.RequestUserDetails;
import com.sparta_logistics.order.presentation.request.OrderCreateRequest;
import jakarta.validation.Valid;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping("")
  public ResponseEntity<OrderCreateResponse> createOrder(
      @RequestBody @Valid OrderCreateRequest req,
      @AuthenticationPrincipal RequestUserDetails user) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(orderService.createOrder(req.toDTO(), user.getUserId(), user.getUsername(), user.getUserSlackId()));
  }

  @GetMapping("")
  public ResponseEntity<List<OrderReadAllResponse>> readAllOrders(
      @AuthenticationPrincipal RequestUserDetails user
  ) {
    return ResponseEntity.ok(orderService.readAllOrders(user.getUserId(), user.getRole()));
  }

  @GetMapping("{orderId}")
  public ResponseEntity<OrderReadResponse> readOrder(
      @PathVariable("orderId") String orderId,
      @AuthenticationPrincipal RequestUserDetails user) {
    return ResponseEntity.ok(orderService.readOrder(orderId, user.getUserId(), user.getRole()));
  }

  @Secured({"ROLE_MASTER", "ROLE_HUB_MANAGER"})
  @PutMapping("{orderId}")
  public ResponseEntity<OrderUpdateResponse> updateOrder(
      @PathVariable("orderId") String orderId,
      @RequestBody @Valid OrderUpdateRequest req,
      @AuthenticationPrincipal RequestUserDetails user) {
    return ResponseEntity.ok(
        orderService.updateOrder(orderId, user.getUserId(), user.getRole(), req.toDTO()));
  }

  @Secured({"ROLE_MASTER", "ROLE_HUB_MANAGER"})
  @PatchMapping("{orderId}")
  public ResponseEntity<OrderUpdateResponse> cancelOrder(
      @PathVariable("orderId") String orderId,
      @AuthenticationPrincipal RequestUserDetails user) {
    return ResponseEntity.ok(orderService.cancelOrder(orderId, user.getUserId(), user.getRole()));
  }

  @Secured({"ROLE_MASTER", "ROLE_HUB_MANAGER"})
  @DeleteMapping("{orderId}")
  public ResponseEntity<OrderDeleteResponse> deleteOrder(
      @PathVariable("orderId") String orderId,
      @AuthenticationPrincipal RequestUserDetails user) {
    return ResponseEntity.ok(orderService.deleteOrder(orderId, user.getUserId(), user.getRole()));
  }

}
