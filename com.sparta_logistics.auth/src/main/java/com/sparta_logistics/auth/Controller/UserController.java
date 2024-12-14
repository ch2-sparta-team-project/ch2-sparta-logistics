package com.sparta_logistics.auth.Controller;

import com.sparta_logistics.auth.Dto.AuthResponseDto;
import com.sparta_logistics.auth.Dto.UserChangePasswordReqDto;
import com.sparta_logistics.auth.Dto.UserPageResponseDto;
import com.sparta_logistics.auth.Dto.UserUpdateRequestDto;
import com.sparta_logistics.auth.Security.UserDetailsImpl;
import com.sparta_logistics.auth.Service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/users")
@Slf4j
public class UserController {

  private final AuthService authService;

  public UserController(AuthService authService) {
    this.authService = authService;
  }

//   패스워드 변경
  @PutMapping("/password")
  public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserChangePasswordReqDto request
  ) {
    authService.changePassword(userDetails, request);
    return ResponseEntity.ok(new AuthResponseDto("비밀번호 변경 완료",200));
  }

  // 사용자 목록 전체 조회(MASTER)
  @Secured("ROLE_MASTER")
  @GetMapping
  public ResponseEntity<AuthResponseDto> users(
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size){
    Page<UserPageResponseDto> Users = authService.getAllUsers(sortBy, page, size);
    return ResponseEntity.ok().body(new AuthResponseDto("유저 정보 검색 완료", HttpStatus.OK.value(), Users));
  }

  // 사용자 단일 조회
  @GetMapping("/{userId}")
  public ResponseEntity<?> userDetails(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String userId) {
    return null;
  }

  // 사용자 생성 구현 고민
  // 코드 구현하기

  // 사용자 수정
  @PatchMapping
  public ResponseEntity<?> userUpdate(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody
      UserUpdateRequestDto request){
    authService.update(userDetails, request);
    return ResponseEntity.ok(new AuthResponseDto("회원 정보 수정 완료", HttpStatus.OK.value()));
  }

  // 회원 삭제
  @DeleteMapping("/delete")
  public ResponseEntity<?> softDeleteUser(@RequestHeader("Authorization") String token) {
    String accessToken = token.replace("Bearer ", "");
    authService.softDeleteUser(accessToken);
    return ResponseEntity.ok(new AuthResponseDto("회원 삭제 완료", 200));
  }

  // 회원 정보 확인
  @GetMapping("/info")
  public ResponseEntity<?> userInfo(@RequestHeader("Authorization") String token) {
    String accessToken = token.replace("Bearer ", "");
    return ResponseEntity.ok(authService.getUserInfoFromAccessToken(accessToken));
  }

}
