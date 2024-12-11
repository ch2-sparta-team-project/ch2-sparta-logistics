package com.sparta_logistics.order.presentation.controller;

import com.sparta_logistics.order.application.dto.OrderCreateResponse;
import com.sparta_logistics.order.application.service.OrderService;
import com.sparta_logistics.order.presentation.dto.RequestUserDetails;
import com.sparta_logistics.order.presentation.request.OrderCreateRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping("")
  public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody @Valid OrderCreateRequest req,
      @AuthenticationPrincipal RequestUserDetails user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(req.toDTO(user.getUserId())));
  }

}
