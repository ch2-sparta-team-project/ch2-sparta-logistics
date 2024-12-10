package com.sparta_logistics.auth.Controller;

import com.sparta_logistics.auth.Dto.AuthResponse;
import com.sparta_logistics.auth.Dto.SignInRequestDto;
import com.sparta_logistics.auth.Dto.SignUpRequestDto;
import com.sparta_logistics.auth.Service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;


  private final String serverPort;

  public AuthController(AuthService authService, @Value("${server.port}") String ServerPort) {
    this.authService = authService;
    this.serverPort = ServerPort;
  }

  @PostMapping("/SignUp")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody SignUpRequestDto SignUpRequestDto){
    return ResponseEntity.ok((authService.createUser(SignUpRequestDto)));
  }

  @PostMapping("/SignIn")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody SignInRequestDto signInRequestDto){
    final AuthResponse response = authService.createAccessToken(signInRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    return ResponseEntity.ok(response + "로그인이 완료되었습니다.");
  }

}