package com.sparta_logistics.hub.presentation.controller;

import com.sparta_logistics.hub.application.service.HubService;
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
import com.sparta_logistics.hub.util.RoleValidator;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {

  private final HubService hubService;

  //  허브 생성
  @PostMapping
  public HubCreateResponse createHub(@RequestBody @Valid HubCreateRequest hubCreateRequest,
                        @RequestHeader(value = "X-User-Id") String userId,
                        @RequestHeader(value = "X-User-role") String userRole
  ) {
    RoleValidator.validateIsHubManager(userRole);
    return hubService.createHub(hubCreateRequest, userId);
  }

  //허브 목록 조회
  @GetMapping
  public Page<HubReadResponse> getHubs(HubSearchRequest searchDto,
      Pageable pageable) {
    return hubService.getHubs(searchDto, pageable);
  }

  @GetMapping("/all")
  public List<HubReadResponse> getAllHubs() {
    return hubService.getAllHubs();
  }

  //허브 단일 조회
  @GetMapping("/{hub_id}")
  public HubReadResponse getHub(@PathVariable(value = "hub_id") UUID hubId) {
    return hubService.getHub(hubId);
  }

  //허브 정보 수정
  @PutMapping("/{hub_id}")
  public HubReadResponse updateHub(@PathVariable(value = "hub_id") UUID hubId,
      @RequestHeader(value = "X-User-role") String userRole,
      @RequestBody HubUpdateRequest updateRequest) {
    RoleValidator.validateIsHubManager(userRole);
    return hubService.updateHub(hubId, updateRequest);
  }

  //허브 삭제
  @DeleteMapping("/{hub_id}")
  public String deleteHub(@PathVariable(value = "hub_id") UUID hubId,
      @RequestHeader(value = "X-User-Id") String userId,
      @RequestHeader(value = "X-User-role") String userRole
  ) {
    RoleValidator.validateIsHubManager(userRole);
    return hubService.deleteHub(hubId, userId);
  }

  // 허브 복원
  @PatchMapping("/{hub_id}")
  public String restoreHub(@PathVariable(value = "hub_id") UUID hubId,
      @RequestHeader(value = "X-User-role") String userRole
      ) {
    RoleValidator.validateIsHubManager(userRole);
    return hubService.restoreHub(hubId);
  }

  //인접 허브 추가(중심 허브의 인접 허브 목록에 허브 추가)
  @PostMapping("/center/{hub_id}")
  public List<HubReadResponse> addNearHubList(@PathVariable(value = "hub_id") UUID hubId,
      @RequestHeader(value = "X-User-role") String userRole,
      @RequestBody NearHubAddRequest nearHubAddRequest) {
    RoleValidator.validateIsHubManager(userRole);
    return hubService.addNearHubList(hubId, nearHubAddRequest);
  }

  //중심 허브에 인접 허브 제거
  @DeleteMapping("/center/{hub_id}")
  public String removeNearHubList(@PathVariable(value = "hub_id") UUID hubId,
      @RequestHeader(value = "X-User-role") String userRole,
      @RequestBody NearHubRemoveRequest nearHubRemoveRequest) {
    RoleValidator.validateIsHubManager(userRole);
    return hubService.removeNearHubList(hubId, nearHubRemoveRequest);
  }

  //중심 허브 설정 활성화/비활성화 (일반 허브 <-> 중심 허브 변경)
  @PatchMapping("/center/{hub_id}")
  public String handleCenterHubSetting(@PathVariable(value = "hub_id") UUID hubId,
      @RequestHeader(value = "X-User-role") String userRole) {
    RoleValidator.validateIsHubManager(userRole);
    return hubService.handleCenterHubSetting(hubId);
  }

  //중심 허브 변경(일반 허브의 중심 허브 수정)
  @PutMapping("/center/{hub_id}")
  public String changeCenterHub(@PathVariable(value = "hub_id") UUID hubId,
      @RequestHeader(value = "X-User-role") String userRole,
      @RequestBody @Valid CenterHubChangeRequest centerHubChangeRequest) {
    RoleValidator.validateIsHubManager(userRole);
    return hubService.changeCenterHub(hubId, centerHubChangeRequest);
  }

  // 허브 경로 생성
  @PostMapping("/hub_route")
  public void createHubRoute(@RequestBody @Valid List<HubRouteCreateRequest> request,
      @RequestHeader(value = "X-User-role") String userRole){
    RoleValidator.validateIsHubManager(userRole);
    hubService.createHubRoute(request);
  }

  // 허브 경로 조회
  @GetMapping("/hub_route")
  public ResponseEntity<HubRouteReadResponse> readHubRoute(HubRouteReadRequest request){
    return hubService.readHubRoute(request);
  }

  @GetMapping("/hub_route/all")
  public List<HubRouteReadResponse> readHubRoutes(){
    return hubService.readAllHubRoute();
  }

  // 허브 경로 수정
  @PutMapping("/hub_route")
  public String updateHubRoute(@RequestBody @Valid HubRouteUpdateRequest request,
      @RequestHeader(value = "X-User-role") String userRole){
    RoleValidator.validateIsHubManager(userRole);
    return hubService.updateHubRoute(request);
  }

  // 허브 경로 삭제
  @DeleteMapping("/hub_route")
  public String deleteHubRoute(@RequestBody @Valid HubRouteDeleteRequest request,
      @RequestHeader(value = "X-User-role") String userRole){
    RoleValidator.validateIsHubManager(userRole);
    return hubService.deleteHubRoute(request);
  }

  // 개발용 api
  @PostMapping("/temp")
  public UUID generateRandomUUID(){
    return UUID.randomUUID();
  }
}
