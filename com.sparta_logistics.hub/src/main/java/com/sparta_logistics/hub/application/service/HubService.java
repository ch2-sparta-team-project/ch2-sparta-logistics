package com.sparta_logistics.hub.application.service;

import com.sparta_logistics.hub.domain.model.Hub;
import com.sparta_logistics.hub.infrastructure.repository.HubRepository;
import com.sparta_logistics.hub.presentation.request.HubCreateRequest;
import com.sparta_logistics.hub.presentation.request.HubSearchRequest;
import com.sparta_logistics.hub.presentation.request.HubUpdateRequest;
import com.sparta_logistics.hub.presentation.response.HubCreateResponse;
import com.sparta_logistics.hub.presentation.response.HubReadResponse;
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

  @Transactional(readOnly = true)
  public Page<HubReadResponse> getHubs(HubSearchRequest searchDto, Pageable pageable) {
    return hubRepository.searchHubs(searchDto, pageable);
  }

  @Transactional(readOnly = true)
  public HubReadResponse getHub(UUID hubId) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    return HubReadResponse.buildResponseByEntity(hub);
  }

  public HubReadResponse updateHub(UUID hubId, HubUpdateRequest updateRequest) {
    Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId);
    hub.updateHub(updateRequest.userId(),
        updateRequest.name(),
        updateRequest.address(),
        updateRequest.longitude(),
        updateRequest.latitude(),
        updateRequest.isCenter());
    return HubReadResponse.buildResponseByEntity(hub);
  }
}
