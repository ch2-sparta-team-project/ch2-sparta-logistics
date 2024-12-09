package com.sparta_logistics.auth.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${service.jwt.access-expiration}")
  private String accessExpiration;

  private final SecretKey secretKey;

  public AuthService(@Value("${service.jwt.secret-key}") String secretKey){
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
  }

  public String createAccessToken(String userId){
    log.info("Creating access token for user {}", userId);
    return Jwts.builder()
        .claim("userId",userId)
        .claim("role","ADMIN")
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + Long.parseLong(accessExpiration)))
        .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
        .compact();

  }

}