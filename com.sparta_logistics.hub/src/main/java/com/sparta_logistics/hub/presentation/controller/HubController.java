package com.sparta_logistics.hub.presentation.controller;

import com.sparta_logistics.hub.application.service.HubService;
import com.sparta_logistics.hub.presentation.request.HubCreateRequest;
import com.sparta_logistics.hub.presentation.response.CommonResponse;
import com.sparta_logistics.hub.presentation.response.HubCreateResponse;
import com.sparta_logistics.hub.util.RoleValidator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
  public CommonResponse<HubCreateResponse> createHub(@RequestBody HubCreateRequest hubCreateRequest
//                        @RequestHeader(value = "X-User-Id") UUID userId,
//                        @RequestHeader(value = "X-User-role") String userRole
  ) {
    RoleValidator.validateIsMaster("Master"); // 헤더에서 값이 넘어오게되면 변경 예정
    return hubService.createHub(hubCreateRequest, UUID.randomUUID()); // 헤더에서 유저 아이디 넘어오게되면 변경 예정
  }

  //허브 목록 조회

  //허브 단일 조회

  //허브 정보 수정

  //허브 삭제

  //중심 허브 여부 변경

}
