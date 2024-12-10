package com.sparta_logistics.auth.Service;

import com.sparta_logistics.auth.Dto.AuthResponse;
import com.sparta_logistics.auth.Dto.SignInRequestDto;
import com.sparta_logistics.auth.Dto.SignUpRequestDto;
import com.sparta_logistics.auth.Entity.Role;
import com.sparta_logistics.auth.Entity.User;
import com.sparta_logistics.auth.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${service.jwt.access-expiration}")
  private String accessExpiration;

  private final SecretKey secretKey;

  // Token 식별자
  public static final String BEARER_PREFIX = "Bearer ";
  // Header KEY 값
  public static final String AUTHORIZATION_HEADER = "Authorization";

  public AuthService(@Value("${service.jwt.secret-key}") String secretKey,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  //토큰 생성
  public AuthResponse createAccessToken(final SignInRequestDto signInRequestDto) {
    log.info("Creating access token for user {}", signInRequestDto.getUsername());

    return userRepository.findByUsername(signInRequestDto.getUsername())
        .filter(user -> passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword()))
        .map(user -> AuthResponse.of(BEARER_PREFIX + Jwts.builder()
            .claim("id", signInRequestDto.getUsername())
            .issuer(issuer)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + Long.parseLong(accessExpiration)))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact())
        ).orElseThrow();

  }


  //회원 가입
  @Transactional
  public String createUser(SignUpRequestDto signUpRequestDto) {
    log.info("Creating user");
    validateDuplicateUser(signUpRequestDto);
    Role role = validateAndGetRole(signUpRequestDto.getRole());
    User user = User.create(signUpRequestDto.getUsername(), signUpRequestDto.getPassword(), signUpRequestDto.getSlackId(), role);

     userRepository.save(user);
    return "회원 가입 성공";
  }

  // 회원가입시 회원 존재 여부 검증
  private void validateDuplicateUser(SignUpRequestDto signUpRequestDto) {
    if (userRepository.findBySlackId(signUpRequestDto.getSlackId()).isPresent()) {
      throw new RuntimeException("Slack ID가 존재합니다.");
    }

    if (userRepository.findByUsername(signUpRequestDto.getUsername()).isPresent()) {
      throw new RuntimeException("이미 존재하는 회원명입니다.");
    }


  }

  //Role 설정
  public Role validateAndGetRole(Role requestRole) {
    if (requestRole == Role.MASTER) {
      return Role.MASTER;
    } else if (requestRole == Role.HUB_MANAGER) {
      return Role.HUB_MANAGER;
    } else if (requestRole == Role.HUB_TO_COMPANY_DELIVERY) {
      return Role.HUB_TO_COMPANY_DELIVERY;
    } else if (requestRole == Role.HUB_TO_HUB_DELIVERY) {
      return Role.HUB_TO_HUB_DELIVERY;
    } else
      return Role.COMPANY_MANAGER;
  };
}