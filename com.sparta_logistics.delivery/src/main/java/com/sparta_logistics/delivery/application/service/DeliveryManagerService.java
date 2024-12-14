package com.sparta_logistics.delivery.application.service;

import com.sparta_logistics.delivery.application.dto.deliverymanager.DeliveryManagerCreateDto;
import com.sparta_logistics.delivery.application.dto.deliverymanager.DeliveryManagerResponse;
import com.sparta_logistics.delivery.application.dto.deliverymanager.DeliveryManagerUpdateDto;
import com.sparta_logistics.delivery.application.port.CompanyClientPort;
import com.sparta_logistics.delivery.application.port.HubClientPort;
import com.sparta_logistics.delivery.application.port.UserClientPort;
import com.sparta_logistics.delivery.domain.model.DeliveryManager;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import com.sparta_logistics.delivery.global.exception.ApplicationException;
import com.sparta_logistics.delivery.global.exception.ErrorCode;
import com.sparta_logistics.delivery.infrastructure.DeliveryManagerRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryManagerService {

  private final DeliveryManagerRepository deliveryManagerRepository;
  private final UserClientPort userClientPort;
  private final HubClientPort hubClientPort;
  private final CompanyClientPort companyClientPort;

  @Transactional
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


  @Transactional
  public DeliveryManagerResponse update(String deliverManagerId, DeliveryManagerUpdateDto dto,
      String userId, String role) {

    DeliveryManager deliveryManager = deliveryManagerRepository.findById(deliverManagerId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    // validation: 허브 매니저인 경우 담당 허브 소속만 업데이트 가능
    if ("ROLE_HUB_MANAGER".equals(role)) {
      String managingHubId = companyClientPort.findCompanyAffiliationHubIdByUserId(userId);
      if (!managingHubId.equals(deliveryManager.getHubId())) {
        throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
      }
    }

    // validation: 허브가 존재하는지 확인
    if (dto.hubId() != null && !hubClientPort.existsByHubId(dto.hubId())) {
      throw new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION);
    }

    deliveryManager.updateStatus(dto.status());
    deliveryManager.updateHub(dto.hubId());

    // return: Entity -> Response DTO 변환
    return DeliveryManagerResponse.builder()
        .deliveryManagerId(deliveryManager.getId())
        .build();
  }

  @Transactional
  public DeliveryManagerResponse delete(String deliveryManagerId, String userId, String role) {

    DeliveryManager deliveryManager = deliveryManagerRepository.findById(deliveryManagerId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    // validation: 허브 매니저인 경우 담당 허브 소속만 업데이트 가능
    if ("ROLE_HUB_MANAGER".equals(role)) {
      String managingHubId = companyClientPort.findCompanyAffiliationHubIdByUserId(userId);
      if (!managingHubId.equals(deliveryManager.getHubId())) {
        throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
      }
    }

    // business logic: Entity 삭제
    deliveryManagerRepository.deleteById(deliveryManagerId);

    // return: Entity -> Response DTO 변환
    return DeliveryManagerResponse.builder()
        .deliveryManagerId(deliveryManager.getId())
        .build();
  }


  @Transactional(readOnly = true)
  public DeliveryManagerResponse getDeliveryManager(String deliveryManagerId, String userId,
      String role) {

    DeliveryManager deliveryManager = deliveryManagerRepository.findByIdAndDeletedAtIsNull(deliveryManagerId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

    // 허브 매니저 권한 체크: 관리 중인 허브에 속하는 매니저만 접근 가능
    if (role.equals("ROLE_HUB_MANAGER")) {
      String managingHubId = companyClientPort.findCompanyAffiliationHubIdByUserId(userId);
      if (!managingHubId.equals(deliveryManager.getHubId())) {
        throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
      }
    }
    if (role.equals("ROLE_DELIVERY_MANAGER") || role.equals("ROLE_COMPANY_MANAGER")) {
      if (!userId.equals(deliveryManagerId)) {
        throw new ApplicationException(ErrorCode.UNAUTHORIZED_EXCEPTION);
      }
    }

    return DeliveryManagerResponse.builder()
        .deliveryManagerId(deliveryManager.getId())
        .hubId(deliveryManager.getHubId())
        .role(deliveryManager.getRole())
        .status(deliveryManager.getStatus())
        .turn(deliveryManager.getTurn())
        .build();
  }

  @Transactional(readOnly = true)
  public List<DeliveryManagerResponse> getAllDeliveryManagers(String userId, String role) {

    // ROLE_MASTER 일 경우 전부 조회
    // ROLE_HUB_MANAGER 일 경우 담당 허브만 조회
    // 아닐 경우 본인의 주문만 조회
    List<DeliveryManager> deliveryManagers = new ArrayList<>();

    // ROLE_MASTER 일 경우 전부 조회
    if ("ROLE_MASTER".equals(role)) {
      deliveryManagers = deliveryManagerRepository.findAllByDeletedAtIsNull();
    }
    // ROLE_HUB_MANAGER 일 경우 담당 허브만 조회
    if ("ROLE_HUB_MANAGER".equals(role)) {
      String managingHub = companyClientPort.findCompanyAffiliationHubIdByUserId(userId);

      deliveryManagers = deliveryManagerRepository.findAllByDeletedAtIsNull();
      deliveryManagers = deliveryManagers.stream()
          .filter(deliveryManager ->
            managingHub.equals(deliveryManager.getHubId())
          )
          .toList();
    }
    // 그 외 권한일 경우 본인의 주문만 조회
    if (role.equals("ROLE_DELIVERY_MANAGER") || role.equals("ROLE_COMPANY_MANAGER")){
      deliveryManagers = deliveryManagerRepository.findAllByIdAndDeletedAtIsNull(userId);
    }

    return deliveryManagers.stream()
        .map(m -> DeliveryManagerResponse.builder()
            .deliveryManagerId(m.getId())
            .hubId(m.getHubId())
            .role(m.getRole())
            .status(m.getStatus())
            .turn(m.getTurn())
            .build())
        .toList();
  }
}
