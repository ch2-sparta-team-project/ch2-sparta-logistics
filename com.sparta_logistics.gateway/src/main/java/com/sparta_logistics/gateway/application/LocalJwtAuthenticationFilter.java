package com.sparta_logistics.gateway.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter {

  @Value("${service.jwt.secret-key}")
  private String secretKey;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    if (path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/sign-up")) {
      return chain.filter(exchange);
    }

    String token = extractToken(exchange);

    if (token == null || !validateToken(token, exchange)) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    getClaimsJwsValue(exchange, chain);

    return chain.filter(exchange);
  }

  private String extractToken(ServerWebExchange exchange) {
    String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }

  private boolean validateToken(String token, ServerWebExchange exchange) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
      Jws<Claims> claimsJws = Jwts.parser()
          .verifyWith(key)
          .build().parseSignedClaims(token);
      log.info("payload :: " + claimsJws.getPayload().toString());

      Claims claims = claimsJws.getPayload();
      exchange.getRequest().mutate()
          .header("X-User-Id", claims.get("userId").toString())
          .header("X-User-Name", claims.get("userName").toString())
          .header("X-Role", claims.get("role").toString())
          .build();

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private Jws<Claims> getClaimsJwsValue(ServerWebExchange exchange, GatewayFilterChain chain) {
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));

    Jws<Claims> claimsJws = Jwts.parser()
        .verifyWith(key)
        .build().parseSignedClaims(extractToken(exchange));
    Claims claims = claimsJws.getPayload();
    exchange.getResponse().getHeaders().add("X-User-Id", claims.get("userId").toString());
    exchange.getResponse().getHeaders().add("X-User-Name", claims.get("userName").toString());
    exchange.getResponse().getHeaders().add("X-User-role", claims.get("role").toString());

    return claimsJws;
  }



}