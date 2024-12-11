package com.sparta_logistics.auth.Security;

import com.sparta_logistics.auth.Entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class JwtUtil {

  private static final long EXPIRATION_TIME = 3600000;

  public static final String AUTHORIZATION_HEADER = "Authorization";

  private static final String BEARER_PREFIX = "Bearer ";

  @Value("${service.jwt.secret-key}")
  private String secretKey;

  private Key key;

  @PostConstruct
  public void init() {
    byte[] decodedKey = Base64.getDecoder().decode(secretKey);
    this.key = Keys.hmacShaKeyFor(decodedKey);
  }

  // 토큰 생성
  public String generateToken(String username, Role role, String slackId) {
    return Jwts.builder()
        .subject(username)
        .claim("username", username)
        .claim("role", role)
        .claim("slackId", slackId)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
  }

  // 토큰 검증
  public boolean validateToken(String token) {
    try {
      if (token == null) {
        log.error("token을 찾을 수 없습니다.");
        return false;
      }
      Jwts.parser()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return false;
  }

  //토큰 가져오기 헤더
  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(BEARER_PREFIX.length()).trim();
    }
    return null;
  }

  // 토큰에서 사용자 정보 가져오기
  public Claims getUserInfoFromToken(String token) {
    return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

}
