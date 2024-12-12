package com.sparta_logistics.auth.Controller;

import com.sparta_logistics.auth.Dto.SignUpRequestDto;
import com.sparta_logistics.auth.Service.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

  private final UserService userService;

  @Getter
  private final String serverPort;

  public UserController(UserService userService, @Value("${server.port}") String serverPort) {
    this.userService = userService;
    this.serverPort = serverPort;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto SignUpRequestDto) {
    return ResponseEntity.ok((userService.signUp(SignUpRequestDto)));
  }

  @GetMapping("/info")
  public ResponseEntity<?> userInfo(@RequestHeader("Authorization") String token) {
    String accessToken = token.replace("Bearer ", "");
    return ResponseEntity.ok(userService.getUserInfoFromAccessToken(accessToken));
  }

}