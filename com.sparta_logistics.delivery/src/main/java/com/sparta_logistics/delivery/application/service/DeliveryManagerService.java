package com.sparta_logistics.delivery.application.service;

import com.sparta_logistics.delivery.application.dto.DeliveryManagerCreateDto;
import com.sparta_logistics.delivery.application.dto.DeliveryManagerResponse;
import com.sparta_logistics.delivery.application.port.CompanyClientPort;
import com.sparta_logistics.delivery.application.port.UserClientPort;
import com.sparta_logistics.delivery.domain.model.DeliveryManager;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import com.sparta_logistics.delivery.global.exception.ApplicationException;
import com.sparta_logistics.delivery.global.exception.ErrorCode;
import com.sparta_logistics.delivery.infrastructure.DeliveryManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryManagerService {

  private final DeliveryManagerRepository deliveryManagerRepository;
  private final UserClientPort userClientPort;
  private final CompanyClientPort companyClientPort;

  public DeliveryManagerResponse create(DeliveryManagerCreateDto dto, String apiCallUserId,
      String apiCallUserRole) {

    // validation: 허브 매니저인 경우 담당 허브 소속만 생성 가능
    if ("ROLE_HUB_MANAGER".equals(apiCallUserRole)) {
      String managingHubId = companyClientPort.findCompanyAffiliationHubIdByUserId(apiCallUserId);
      if (!managingHubId.equals(dto.hubId())) {
        throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
      }
    }
    
    // validation: user id 가 존재하는지 검증 우선
    if (!userClientPort.existsByUserId(dto.userId())) {
      throw new ApplicationException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
    }

    // business logic: turn 계산 후 주입
    int turn;
    if (dto.role().equals(DeliveryManagerRole.HUB_DELIVERY_MANAGER)) {
      Integer maxTurn = deliveryManagerRepository.findMaxTurnByRole(dto.role()).
          orElse(0);
      turn = maxTurn + 1;
    } else {
      Integer maxTurn = deliveryManagerRepository.findMaxTurnByRoleAndHubId(dto.role(), dto.hubId())
          .orElse(0);
      turn = maxTurn + 1;
    }

    // business logic: Entity 생성 시 팩터리 메서드 사용
    DeliveryManager deliveryManager = DeliveryManager.create(
        dto.userId(),
        dto.hubId(),
        dto.role(),
        dto.status(),
        turn
    );

    // business logic: Entity 저장
    DeliveryManager saved = deliveryManagerRepository.save(deliveryManager);

    // return: Entity -> Response DTO 변환
    return DeliveryManagerResponse.builder()
        .deliveryManagerId(saved.getId())
        .build();
  }

}
