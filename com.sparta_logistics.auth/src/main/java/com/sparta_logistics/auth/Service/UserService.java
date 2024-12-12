package com.sparta_logistics.auth.Service;

import com.sparta_logistics.auth.Dto.SignUpRequestDto;
import com.sparta_logistics.auth.Entity.Role;
import com.sparta_logistics.auth.Entity.User;
import com.sparta_logistics.auth.Repository.UserRepository;
import com.sparta_logistics.auth.Security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;


  public UserService(@Value("${service.jwt.secret-key}") String secretKey,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  /*
  * 회원 가입
  * */
  @Transactional
  public String signUp(SignUpRequestDto signUpRequestDto) {
    validateDuplicateUser(signUpRequestDto);

    Role role = validateAndGetRole(signUpRequestDto.getRole());
    User user = User.create(signUpRequestDto.getUserName(), signUpRequestDto.getPassword(), signUpRequestDto.getSlackId(), role, passwordEncoder);

    userRepository.save(user);
    return "회원 가입 성공";
  }

  /*
   * 사용자 UserInfo 확인(auth/info)
   * */
  public Claims getUserInfoFromAccessToken(String accessToken) {
    // 2. 사용자 정보를 DB에서 조회
    return jwtUtil.getUserInfoFromToken(accessToken);
  }


  // 회원가입시 회원 존재 여부 검증
  private void validateDuplicateUser(SignUpRequestDto signUpRequestDto) {
    if (userRepository.findBySlackId(signUpRequestDto.getSlackId()).isPresent()) {
      throw new RuntimeException("Slack ID가 존재합니다.");
    }
    if (userRepository.findByUserName(signUpRequestDto.getUserName()).isPresent()) {
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
  }
}