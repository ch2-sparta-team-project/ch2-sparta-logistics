package com.sparta_logistics.order.application.service;

import com.sparta_logistics.order.application.dto.OrderDeleteResponse;
import com.sparta_logistics.order.application.dto.OrderReadResponse;
import com.sparta_logistics.order.application.dto.OrderUpdateResponse;
import com.sparta_logistics.order.application.mapper.OrderDtoMapper;
import com.sparta_logistics.order.global.exception.ApplicationException;
import com.sparta_logistics.order.global.exception.ErrorCode;
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
import java.util.List;
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
    CompanyCreateDto companyInfo = companyClientPort.findCompanyInfoForCreateByUserId(
        dto.getUserId());
    UserCreateDto userInfo = userClientPort.findUserInfoForCreateByUserId(dto.getUserId());

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
    order.assignDelivery(deliveryId);
    //product 수량 감소
    productClientPort.updateProductQuantity(dto.getQuantity());

    return OrderCreateResponse.builder()
        .orderId(order.getId())
        .productName(dto.getProductName())
        .quantity(order.getQuantity())
        .requestDescription(order.getRequestDescription())
        .build();
  }

  @Transactional(readOnly = true)
  public List<OrderReadResponse> readAllOrders(String userId, String role) {
    // ROLE_MASTER 일 경우 전부 조회
    // ROLE_HUB_MANAGER 일 경우 담당 허브만 조회
    // 아닐 경우 본인의 주문만 조회
    return null;
  }

  @Transactional(readOnly = true)
  public OrderReadResponse readOrder(String orderId, String userId, String role) {

    Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    // ROLE_MASTER 일 경우 모두 조회 가능
    // ROLE_HUB_MANAGER 일 경우 담당 허브만 조회 가능
    // 아닐 경우 본인의 주문만 조회 가능
    if (role.equals("ROLE_HUB_MANAGER")) {
      verifyOrderAccessForHubManager(userId, order);
    }
    if (role.equals("ROLE_DELIVERY_MANAGER") || role.equals("ROLE_COMPANY_MANAGER")) {
      verifyOrderAccessForUser(order.getUserId(), userId);
    }

    // Response 내용 수집 후 반환
    String productName = productClientPort.findProductNameByProductId(order.getProductId());
    String receiverCompanyName = companyClientPort.findCompanyNameByCompanyId(
        order.getReceiverCompanyId());
    String supplierCompanyName = companyClientPort.findCompanyNameByCompanyId(
        order.getSupplierCompanyId());
    String userName = userClientPort.findUserNameByUserId(order.getUserId());

    return OrderDtoMapper.toOrderReadResponse(order, receiverCompanyName, supplierCompanyName,
        userName, productName);
  }

  @Transactional
  public OrderUpdateResponse cancelOrder(String orderId, String userId, String role) {
    // 취소할 Order 찾기
    Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    // 허브 매니저일 경우 담당 허브의 주문만 취소 가능하도록 검증
    if (role.equals("ROLE_HUB_MANAGER")) {
      verifyOrderAccessForHubManager(userId, order);
    }
    // 주문 취소
    order.refund();

    return new OrderUpdateResponse(order.getId());
  }

  @Transactional
  public OrderDeleteResponse deleteOrder(String orderId, String userId, String role) {
    // ?주문이 refunded 되었을 경우만 삭제할 수 있어야 하나?
    // 삭제할 Order 찾기
    Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    // 허브 매니저일 경우 담당 허브의 주문만 삭제 가능하도록 검증
    if (role.equals("ROLE_HUB_MANAGER")) {
      verifyOrderAccessForHubManager(userId, order);
    }

    // Order 와 연관된 Delivery 까지 삭제
    orderRepository.deleteById(orderId);
    deliveryClientPort.deleteDeliveryById(order.getDeliveryId());

    return new OrderDeleteResponse(orderId);
  }

  private void verifyOrderAccessForUser(String userIdInOrder, String userId) {
    if (!userIdInOrder.equals(userId)) {
      throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
    }
  }

  private void verifyOrderAccessForHubManager(String userId, Order order) {
    String userHub = companyClientPort.findCompanyAffiliationHubIdByUserId(
        userId);
    String productHub = productClientPort.findHubIdByProductId(order.getProductId());

    if (!userHub.equals(productHub)) {
      throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
    }
  }
}
