package com.sparta_logistics.hub.application.service;

import com.sparta_logistics.hub.domain.model.Hub;
import com.sparta_logistics.hub.domain.model.HubRoute;
import com.sparta_logistics.hub.infrastructure.repository.HubRepository;
import com.sparta_logistics.hub.infrastructure.repository.HubRouteRepository;
import com.sparta_logistics.hub.presentation.request.CenterHubChangeRequest;
import com.sparta_logistics.hub.presentation.request.HubCreateRequest;
import com.sparta_logistics.hub.presentation.request.HubRouteCreateRequest;
import com.sparta_logistics.hub.presentation.request.HubRouteDeleteRequest;
import com.sparta_logistics.hub.presentation.request.HubRouteReadRequest;
import com.sparta_logistics.hub.presentation.request.HubRouteUpdateRequest;
import com.sparta_logistics.hub.presentation.request.HubSearchRequest;
import com.sparta_logistics.hub.presentation.request.HubUpdateRequest;
import com.sparta_logistics.hub.presentation.request.NearHubAddRequest;
import com.sparta_logistics.hub.presentation.request.NearHubRemoveRequest;
import com.sparta_logistics.hub.presentation.response.HubCreateResponse;
import com.sparta_logistics.hub.presentation.response.HubReadResponse;
import com.sparta_logistics.hub.presentation.response.HubRouteReadResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HubService {

  private final HubRepository hubRepository;
  private final HubRouteRepository hubRouteRepository;

  // 허브 생성
  public HubCreateResponse createHub(HubCreateRequest hubCreateRequest,
      String userId) {
    Hub hub = hubRepository.save(Hub.createHub(
        UUID.fromString(userId),
        hubCreateRequest.name(),
        hubCreateRequest.address(),
        hubCreateRequest.longitude(),
        hubCreateRequest.latitude(),
        hubCreateRequest.isCenter()));
    if (hubCreateRequest.isCenter()) {
      hub.setCenterHub(hub);
    }
    return HubCreateResponse.buildResponseByEntity(hub);
  }

  // 허브 목록 조회
  @Transactional(readOnly = true)
  public Page<HubReadResponse> getHubs(HubSearchRequest searchDto, Pageable pageable) {
    return hubRepository.searchHubs(searchDto, pageable);
  }

  // 허브 단일 조회
  @Transactional(readOnly = true)
  public HubReadResponse getHub(UUID hubId) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    if (hub == null) {
      return null;
    }
    return HubReadResponse.buildResponseByEntity(hub);
  }

  // 허브 정보 수정
  public HubReadResponse updateHub(UUID hubId, HubUpdateRequest updateRequest) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    hub.updateHub(updateRequest.userId(),
        updateRequest.name(),
        updateRequest.address(),
        updateRequest.longitude(),
        updateRequest.latitude());
    return HubReadResponse.buildResponseByEntity(hub);
  }

  // 허브 삭제
  public String deleteHub(UUID hubId, String userId) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    //todo: auth 연결 시 컨트롤러에서 받아오는 식으로 변경 필요
    hub.deleteHub(userId);
    return "허브 삭제가 완료되었습니다.";
  }

  // 허브 복원
  public String restoreHub(UUID hubId) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNotNull(hubId);
    hub.restoreHub();
    return "허브가 복원되었습니다.";
  }

  // 중심 허브에 인접 허브 추가
  public List<HubReadResponse> addNearHubList(UUID hubId, NearHubAddRequest nearHubAddRequest) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    List<Hub> hubList = hubRepository.findByIdIn(nearHubAddRequest.nearHubList());
    hubList.forEach(hub::addNearHub);
    return hubList.stream()
        .map(HubReadResponse::buildResponseByEntity).toList();
  }

  // 중심 허브의 인접 허브 제거
  public String removeNearHubList(UUID hubId,
      NearHubRemoveRequest nearHubRemoveRequest) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    List<Hub> hubList = hubRepository.findByIdIn(nearHubRemoveRequest.nearHubList());
    hubList.forEach(hub::removeNearHub);
    return "요청한 허브 목록이 " + hub.getName() + "의 인접 허브 목록에서 제거되었습니다.";
  }

  // 중심 허브 설정 활성화/비활성화
  public String handleCenterHubSetting(UUID hubId) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    if (hub.getIsCenter()) {
      List<Hub> nearHubList = hubRepository.findByCenterHub(hub);
      nearHubList.forEach(nearHub -> nearHub.setCenterHub(null));
      hub.deactivateCenterHub();
      return hub.getName() + "가 일반 허브로 설정되었습니다.";
    }
    hub.activateCenterHub();
    return hub.getName() + "가 중심 허브로 설정되었습니다.";
  }

  // 중심 허브 변경
  public String changeCenterHub(UUID hubId, CenterHubChangeRequest centerHubChangeRequest) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    Hub newCenterHub = hubRepository.findByIdAndDeletedAtIsNull(
        centerHubChangeRequest.centerHubId());
    if (!newCenterHub.getIsCenter()) {
      throw new IllegalArgumentException("중심 허브가 아닌 허브를 중심 허브로 설정할 수 없습니다.");
    }
    hub.setCenterHub(newCenterHub);
    return hub.getName() + "의 중심 허브가 " + newCenterHub.getName() + "로 변경되었습니다.";
  }

  public void createHubRoute(List<HubRouteCreateRequest> request) {
    for (HubRouteCreateRequest hubRouteCreateRequest : request) {
      Hub sourceHub = hubRepository.findByNameAndDeletedAtIsNull(
          hubRouteCreateRequest.sourceHubName());
      Hub destinationHub = hubRepository.findByNameAndDeletedAtIsNull(
          hubRouteCreateRequest.destinationHubName());
      HubRoute hubRoute =
          HubRoute.createHubRoute(sourceHub, destinationHub, hubRouteCreateRequest.duration(),
              hubRouteCreateRequest.distance());
      hubRouteRepository.save(hubRoute);
    }
  }

  public ResponseEntity<HubRouteReadResponse> readHubRoute(HubRouteReadRequest request) {
    HubRoute hubRoute = hubRouteRepository.findBySourceHubNameAndDestinationHubName(
        request.sourceHubName(), request.destinationHubName());
    return ResponseEntity.ok(HubRouteReadResponse.buildResponseByEntity(hubRoute));
  }

  public String updateHubRoute(HubRouteUpdateRequest request) {
    HubRoute hubRoute = hubRouteRepository.findBySourceHubNameAndDestinationHubName(
        request.sourceHubName(), request.destinationHubName());
    hubRoute.update(request.duration(), request.distance());
    return "허브 경로 수정이 완료되었습니다.";
  }

  public String deleteHubRoute(HubRouteDeleteRequest request) {
    hubRouteRepository.deleteBySourceHubNameAndDestinationHubName(request.sourceHubName(),
        request.destinationHubName());
    hubRouteRepository.deleteBySourceHubNameAndDestinationHubName(request.destinationHubName(),
        request.sourceHubName());
    return "허브 경로 삭제가 완료되었습니다.";
  }

  public List<HubReadResponse> getAllHubs() {
    return hubRepository.findAllByDeletedAtIsNull()
        .stream()
        .map(HubReadResponse::buildResponseByEntity)
        .toList();
  }

  public List<HubRouteReadResponse> readAllHubRoute() {
    return hubRouteRepository.findAll().stream()
        .map(HubRouteReadResponse::buildResponseByEntity)
        .toList();
  }
}
