package com.sparta_logistics.hub.application.service;

import com.sparta_logistics.hub.domain.model.Hub;
import com.sparta_logistics.hub.domain.repository.HubRepository;
import com.sparta_logistics.hub.presentation.request.HubCreateRequest;
import com.sparta_logistics.hub.presentation.response.CommonResponse;
import com.sparta_logistics.hub.presentation.response.HubCreateResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HubService {

  private final HubRepository hubRepository;

  // 허브 생성
  public CommonResponse<HubCreateResponse> createHub(HubCreateRequest hubCreateRequest,
      UUID userId) {
    Hub hub = hubRepository.save(Hub.createHub(
        userId,
        hubCreateRequest.name(),
        hubCreateRequest.address(),
        hubCreateRequest.longitude(),
        hubCreateRequest.latitude(),
        hubCreateRequest.isCenter()));
    HubCreateResponse hubCreateResponse = HubCreateResponse.buildResponseByEntity(hub);
    return new CommonResponse<>(LocalDateTime.now(), 200, "허브가 생성되었습니다.", hubCreateResponse);
  }
}
