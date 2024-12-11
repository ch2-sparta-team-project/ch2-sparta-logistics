package com.sparta_logistics.order.application.service;

import com.sparta_logistics.order.infrastructure.client.dto.CompanyCreateDto;
import com.sparta_logistics.order.infrastructure.client.dto.DeliveryCreateRequest;
import com.sparta_logistics.order.application.dto.OrderCreateDto;
import com.sparta_logistics.order.application.dto.OrderCreateResponse;
import com.sparta_logistics.order.infrastructure.client.dto.UserCreateDto;
import com.sparta_logistics.order.application.port.CompanyClientPort;
import com.sparta_logistics.order.application.port.DeliveryClientPort;
import com.sparta_logistics.order.application.port.ProductClientPort;
import com.sparta_logistics.order.application.port.UserClientPort;
import com.sparta_logistics.order.domain.model.Order;
import com.sparta_logistics.order.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

  private final OrderRepository orderRepository;
  private final DeliveryClientPort deliveryClientPort;
  private final CompanyClientPort companyClientPort;
  private final ProductClientPort productClientPort;
  private final UserClientPort userClientPort;

  @Transactional
  public OrderCreateResponse createOrder(OrderCreateDto dto) {

    //Delivery 를 생성할 때 필요한 정보 생성
    //외부 포트 연결
    String sourceHubId = productClientPort.findHubIdByProductId(dto.getProductId());
    CompanyCreateDto companyInfo = companyClientPort.findCompanyInfoByUserId(dto.getUserId());
    UserCreateDto userInfo = userClientPort.findUserInfoByUserId(dto.getUserId());

    //Dto 를 Entity 로 변환
    Order order = Order.create(
        dto.getSupplierCompanyId(),
        companyInfo.companyId(),
        dto.getUserId(),
        dto.getProductId(),
        "",
        dto.getQuantity(),
        dto.getRequestDescription(),
        dto.getIsRefunded()
    );

    //Order Save
    orderRepository.save(order);

    //Delivery 생성
    String deliveryId = deliveryClientPort.createDelivery(
        DeliveryCreateRequest.builder()
            .sourceHubId(sourceHubId)
            .orderId(order.getId())
            .address(companyInfo.address())
            .recipientName(userInfo.name())
            .recipientSlackId(userInfo.slackId())
            .build()
    );

    //Order 에 DeliveryId 연결
    order.updateDeliveryId(deliveryId);
    //product 수량 감소
    productClientPort.updateProductQuantity(dto.getQuantity());

    return OrderCreateResponse.builder()
        .orderId(order.getId())
        .productName(dto.getProductName())
        .quantity(order.getQuantity())
        .requestDescription(order.getRequestDescription())
        .build();
  }


}