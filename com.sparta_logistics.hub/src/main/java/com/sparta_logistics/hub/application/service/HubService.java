package com.sparta_logistics.hub.application.service;

import com.sparta_logistics.hub.domain.model.Hub;
import com.sparta_logistics.hub.infrastructure.repository.HubRepository;
import com.sparta_logistics.hub.presentation.request.CenterHubChangeRequest;
import com.sparta_logistics.hub.presentation.request.HubCreateRequest;
import com.sparta_logistics.hub.presentation.request.HubSearchRequest;
import com.sparta_logistics.hub.presentation.request.HubUpdateRequest;
import com.sparta_logistics.hub.presentation.request.NearHubAddRequest;
import com.sparta_logistics.hub.presentation.request.NearHubRemoveRequest;
import com.sparta_logistics.hub.presentation.response.HubCreateResponse;
import com.sparta_logistics.hub.presentation.response.HubReadResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HubService {

  private final HubRepository hubRepository;

  // 허브 생성
  public HubCreateResponse createHub(HubCreateRequest hubCreateRequest,
      UUID userId) {
    Hub hub = hubRepository.save(Hub.createHub(
        userId,
        hubCreateRequest.name(),
        hubCreateRequest.address(),
        hubCreateRequest.longitude(),
        hubCreateRequest.latitude(),
        hubCreateRequest.isCenter()));
    if (hubCreateRequest.isCenter()){
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
  public String deleteHub(UUID hubId) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    //todo: auth 연결 시 컨트롤러에서 받아오는 식으로 변경 필요
    hub.deleteHub("temp user");
    return "허브 삭제가 완료되었습니다.";
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
      hub.deactivateCenterHub();
      return hub.getName() + "가 일반 허브로 설정되었습니다.";
    }
    hub.activateCenterHub();
    return hub.getName() + "가 중심 허브로 설정되었습니다.";
  }

  // 중심 허브 변경
  public String changeCenterHub(UUID hubId, CenterHubChangeRequest centerHubChangeRequest) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    Hub newCenterHub = hubRepository.findByIdAndDeletedAtIsNull(centerHubChangeRequest.centerHubId());
    hub.setCenterHub(newCenterHub);
    return hub.getName() + "의 중심 허브가 " + newCenterHub.getName() + "로 변경되었습니다.";
  }
}
