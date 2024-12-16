package com.sparta_logistics.auth.presentation.Controller;

import com.sparta_logistics.auth.presentation.Dto.AuthResponseDto;
import com.sparta_logistics.auth.presentation.Dto.SignUpForCompanyManagerRequestDto;
import com.sparta_logistics.auth.presentation.Dto.SignUpRequestDto;
import com.sparta_logistics.auth.application.Service.AuthService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @Secured("ROLE_MASTER")
  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
    authService.signUp(signUpRequestDto);
    return ResponseEntity.ok(new AuthResponseDto("회원 생성 성공",200));
  }

  @PostMapping("/sign-up/company")
  public ResponseEntity<?> signUpForCompany(@RequestBody SignUpForCompanyManagerRequestDto signUpRequestDto) {
    authService.signUpForCompanyManager(signUpRequestDto);
    return ResponseEntity.ok(new AuthResponseDto("업체 담당자 가입 성공",200));
  }

}