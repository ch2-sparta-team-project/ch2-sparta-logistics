package com.sparta_logistics.auth.Controller;

import com.sparta_logistics.auth.Dto.SignUpDto;
import com.sparta_logistics.auth.Entity.User;
import com.sparta_logistics.auth.Repository.UserRepository;
import com.sparta_logistics.auth.Service.AuthService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @Value("${server.port}") //
  String ServerPort;

  @PostMapping("/auth/Login")
  public ResponseEntity<?> createAuthenticationToken(@RequestParam String userId){
    return ResponseEntity.ok(new AuthResponse(authService.createAccessToken(userId)));
  }


  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class AuthResponse {
    private String access_token;
  }

  public HttpHeaders DefaultHeaders() {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Server-Port", ServerPort);
    return responseHeaders;
  }

}