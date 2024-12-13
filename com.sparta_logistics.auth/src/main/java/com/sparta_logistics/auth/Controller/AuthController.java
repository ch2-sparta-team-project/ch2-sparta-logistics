package com.sparta_logistics.auth.Controller;

import com.sparta_logistics.auth.Dto.SignUpRequestDto;
import com.sparta_logistics.auth.Service.AuthService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthController {

  private final AuthService authService;

  @Getter
  private final String serverPort;

  public AuthController(AuthService authService, @Value("${server.port}") String serverPort) {
    this.authService = authService;
    this.serverPort = serverPort;
  }

  @PostMapping("/auth/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto SignUpRequestDto) {
    return ResponseEntity.ok((authService.signUp(SignUpRequestDto)));
  }

  @GetMapping("/users/info")
  public ResponseEntity<?> userInfo(@RequestHeader("Authorization") String token) {
    String accessToken = token.replace("Bearer ", "");
    return ResponseEntity.ok(authService.getUserInfoFromAccessToken(accessToken));
  }

  @DeleteMapping("/users/delete")
  public ResponseEntity<?> softDeleteUser(@RequestHeader("Authorization") String token) {
    String accessToken = token.replace("Bearer ", "");
    authService.softDeleteUser(accessToken);
    return ResponseEntity.ok("회원 삭제 완료");
  }


}