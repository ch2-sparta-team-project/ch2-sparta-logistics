package com.sparta_logistics.order.presentation.controller;

import com.sparta_logistics.order.application.dto.CreateOrderRes;
import com.sparta_logistics.order.application.service.OrderService;
import com.sparta_logistics.order.presentation.request.CreateOrderReq;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;

  @GetMapping("")
  public ResponseEntity<CreateOrderRes> createOrder(@RequestBody @Valid CreateOrderReq res) {
    return ResponseEntity.ok(orderService.createOrder(res.toDTO(mockUserIdProvider())));

  }

  public String mockUserIdProvider(){
    return UUID.randomUUID().toString();
  }

}
