package com.sparta_logistics.hub.presentation.controller;

import com.sparta_logistics.hub.application.service.HubService;
import com.sparta_logistics.hub.presentation.request.HubCreateRequest;
import com.sparta_logistics.hub.presentation.request.HubSearchRequest;
import com.sparta_logistics.hub.presentation.request.HubUpdateRequest;
import com.sparta_logistics.hub.presentation.response.HubCreateResponse;
import com.sparta_logistics.hub.presentation.response.HubReadResponse;
import com.sparta_logistics.hub.util.RoleValidator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {

  private final HubService hubService;

  //  허브 생성
  @PostMapping
  public HubCreateResponse createHub(@RequestBody HubCreateRequest hubCreateRequest
//                        @RequestHeader(value = "X-User-Id") UUID userId,
//                        @RequestHeader(value = "X-User-role") String userRole
  ) {
    RoleValidator.validateIsMaster("Master"); // 헤더에서 값이 넘어오게되면 변경 예정
    return hubService.createHub(hubCreateRequest, UUID.randomUUID()); // 헤더에서 유저 아이디 넘어오게되면 변경 예정
  }

  //허브 목록 조회
  @GetMapping
  public Page<HubReadResponse> getHubs(HubSearchRequest searchDto,
      Pageable pageable) {
    return hubService.getHubs(searchDto, pageable);
  }

  //허브 단일 조회
  @GetMapping("/{hub_id}")
  public HubReadResponse getHub(@PathVariable(value = "hub_id") UUID hubId) {
    return hubService.getHub(hubId);
  }
  //허브 정보 수정
  @PutMapping("/{hub_id")
  public HubReadResponse updateHub(@PathVariable(value = "hub_id") UUID hubId,
      HubUpdateRequest updateRequest) {
    return hubService.updateHub(hubId, updateRequest);
  }
  //허브 삭제
  @DeleteMapping("/{hub_id")
  public String deleteHub(@PathVariable(value = "hub_id") UUID hubId) {
    return hubService.deleteHub(hubId);
  }
  //중심 허브 설정 활성화 (일반 허브 -> 중심 허브 변경)

  //중심 허브 설정 비활성화 (중심 허브 -> 일반 허브 변경)

  //중심 허브 수정(일반 허브의 중심 허브 수정)

  //인접 허브 추가(중심 허브의 인접 허브 목록에 허브 추가)

}
