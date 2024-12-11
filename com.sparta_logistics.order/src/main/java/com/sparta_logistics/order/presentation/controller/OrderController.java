package com.sparta_logistics.order.presentation.controller;

import com.sparta_logistics.order.application.dto.CreateOrderRes;
import com.sparta_logistics.order.application.service.OrderService;
import com.sparta_logistics.order.presentation.dto.RequestUserDetails;
import com.sparta_logistics.order.presentation.request.CreateOrderReq;
import jakarta.validation.Valid;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping("")
  public ResponseEntity<CreateOrderRes> createOrder(@RequestBody @Valid CreateOrderReq req,
      @AuthenticationPrincipal RequestUserDetails user) {
    return ResponseEntity.ok(orderService.createOrder(req.toDTO(user.getUserId())));
  }

}
