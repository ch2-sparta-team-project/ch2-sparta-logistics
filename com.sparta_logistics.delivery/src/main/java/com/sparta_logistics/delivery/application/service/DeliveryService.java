package com.sparta_logistics.delivery.application.service;

import com.sparta_logistics.delivery.application.dto.delivery.DeliveryCreateDto;
import com.sparta_logistics.delivery.application.dto.delivery.DeliveryResponse;
import com.sparta_logistics.delivery.application.port.HubClientPort;
import com.sparta_logistics.delivery.application.util.HaversineUtil;
import com.sparta_logistics.delivery.domain.model.Delivery;
import com.sparta_logistics.delivery.domain.model.DeliveryHubToCompanyRoute;
import com.sparta_logistics.delivery.domain.model.DeliveryHubToHubRoute;
import com.sparta_logistics.delivery.domain.model.DeliveryManager;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerStatus;
import com.sparta_logistics.delivery.global.exception.ApplicationException;
import com.sparta_logistics.delivery.global.exception.ErrorCode;
import com.sparta_logistics.delivery.infrastructure.client.geocoding.NominatimClient;
import com.sparta_logistics.delivery.infrastructure.dto.HubDto;
import com.sparta_logistics.delivery.infrastructure.dto.HubRouteDto;
import com.sparta_logistics.delivery.infrastructure.repository.DeliveryManagerRepository;
import com.sparta_logistics.delivery.infrastructure.repository.DeliveryRepository;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryManagerRepository deliveryManagerRepository;
  private final HubClientPort hubClientPort;

  @Transactional
  public DeliveryResponse create(DeliveryCreateDto dto) {

    //Delivery Create
    Delivery delivery = Delivery.create(
        dto.sourceHubId(),
        dto.orderId(),
        dto.address(),
        dto.recipientName(),
        dto.recipientSlackId()
    );

    Deque<HubDto> hubRouteSequenceStack = new ArrayDeque<>();

    // Address 를 이용하여 목적지의 위도, 경도 계산
    double[] destinationCoordinates;
    try {
      destinationCoordinates = NominatimClient.getLatLngFromAddress(dto.address());
    } catch (Exception e) {
      throw new ApplicationException(ErrorCode.CLIENT_CALL_EXCEPTION);
    }
    //---
    Map<String, HubDto> hubs = hubClientPort.findHubAll();

    //1. 목적지와 가장 가까운 허브 계산
    HubDto nearestHub = null;
    double nearestHubDistance = Double.MAX_VALUE;
    for (HubDto hub : hubs.values()) {
      double distance = HaversineUtil.calculateDistance(destinationCoordinates[0],
          destinationCoordinates[1], hub.latitude(), hub.longitude());

      if (distance < nearestHubDistance) {
        nearestHubDistance = distance;
        nearestHub = hub;
      }
    }
    hubRouteSequenceStack.push(nearestHub);
    delivery.updateDestinationHubId(nearestHub.hubId());
    //---

    //2. 가장 가까운 허브의 중앙 허브 찾기
    if (!nearestHub.isCenter()) {
      hubRouteSequenceStack.push(hubs.get(nearestHub.centerHubId()));
    }
    //---

    //3. source 허브의 중앙 허브 찾기
    HubDto sourceHub = hubs.get(dto.sourceHubId());
    if (!sourceHub.isCenter()) {
      hubRouteSequenceStack.push(hubs.get(sourceHub.centerHubId()));
    }
    //---

    //4. source 허브 찾기
    hubRouteSequenceStack.push(sourceHub);
    //---

    //companyDeliveryManager 배정
    DeliveryManager companyDeliveryManager = deliveryManagerRepository.findFirstByRoleAndHubIdAndStatusAndDeletedAtIsNullOrderByTurnAsc(
        DeliveryManagerRole.COMPANY_DELIVERY_MANAGER, nearestHub.hubId(),
        DeliveryManagerStatus.NO_WORK).orElse(null);
    //---

    // hubDeliveryManager 배정
    List<DeliveryManager> hubDeliveryManagers = deliveryManagerRepository.findAllByRoleAndStatusAndDeletedAtIsNullOrderByTurnAsc(
        DeliveryManagerRole.HUB_DELIVERY_MANAGER, DeliveryManagerStatus.NO_WORK);
    DeliveryManager hubDeliverManager = null;
    double nearestDeliverDistance = Double.MAX_VALUE;
    for (DeliveryManager manager : hubDeliveryManagers) {
      HubDto managerLocationHub = hubs.get(manager.getHubId());
      double distance = HaversineUtil.calculateDistance(sourceHub.latitude(),
          sourceHub.longitude(), managerLocationHub.latitude(), managerLocationHub.longitude());

      if (distance < nearestDeliverDistance) {
        nearestDeliverDistance = distance;
        hubDeliverManager = manager;
      }
    }
    //---
    Long totalDuration = 0L;
    //엔티티들 매핑
    DeliveryHubToCompanyRoute hubToCompanyRoute = DeliveryHubToCompanyRoute.create(
        companyDeliveryManager,
        delivery,
        nearestHub.hubId(),
        delivery.getAddress(),
        hubRouteSequenceStack.size(),
        nearestHubDistance,
        totalDuration += calculateTravelTime(nearestHubDistance, 60)
    );
    delivery.addRoute(hubToCompanyRoute);

    int sequence = 1;


    Map<String, HubRouteDto> hubRouteAll = hubClientPort.findHubRouteAll();


    while (hubRouteSequenceStack.size() > 1) {
      HubDto popHub = hubRouteSequenceStack.pop();
      HubDto topHub = hubRouteSequenceStack.peek();
      Long duration = hubRouteAll.get(popHub.hubId()).duration();
      totalDuration += duration;
      DeliveryHubToHubRoute hubToHubRoute = DeliveryHubToHubRoute.create(
          hubDeliverManager,
          delivery,
          popHub.hubId(),
          topHub.hubId(),
          sequence,
          hubRouteAll.get(popHub.hubId()).distance(),
          duration
      );
      delivery.addRoute(hubToHubRoute);
      sequence++;
    }

    delivery.updateEstimateArrivalTime(LocalDateTime.now().plusMinutes(totalDuration));
    deliveryRepository.save(delivery);

    return DeliveryResponse.builder()
        .deliveryId(delivery.getId())
        .build();
  }

  private Long calculateTravelTime(double nearestHubDistance, double speedKmPerHour) {
    // 속도가 0 이하인 경우 예외 처리
    if (speedKmPerHour <= 0) {
      throw new IllegalArgumentException("Speed must be greater than 0.");
    }

    // 이동 시간 계산 (시간 단위)
    double travelTimeHours = nearestHubDistance / speedKmPerHour;

    // 이동 시간을 시간 및 분으로 분리
    long hours = (long) travelTimeHours; // 정수 시간
    long minutes = Math.round((travelTimeHours - hours) * 60); // 나머지를 분으로 변환

    return hours*60 + minutes;
  }
}
