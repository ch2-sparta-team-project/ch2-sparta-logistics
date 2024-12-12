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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

  private final AuthService authService;

  @Getter
  private final String serverPort;

  public AuthController(AuthService authService, @Value("${server.port}") String serverPort) {
    this.authService = authService;
    this.serverPort = serverPort;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto SignUpRequestDto) {
    return ResponseEntity.ok((authService.signUp(SignUpRequestDto)));
  }

  @GetMapping("/info")
  public ResponseEntity<?> userInfo(@RequestHeader("Authorization") String token) {
    String accessToken = token.replace("Bearer ", "");
    return ResponseEntity.ok(authService.getUserInfoFromAccessToken(accessToken));
  }

  @GetMapping("/token")
  public ResponseEntity<?> hi(@RequestParam String user_id){

    return ResponseEntity.ok(new AuthResponse(authService.createAccessTokenTemp(user_id)));
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class AuthResponse{
    private String access_token;
  }

}