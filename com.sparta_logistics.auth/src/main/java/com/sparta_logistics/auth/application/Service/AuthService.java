package com.sparta_logistics.auth.application.Service;

import com.sparta_logistics.auth.application.global.exception.ErrorCode;
import com.sparta_logistics.auth.application.global.exception.AuthException;
import com.sparta_logistics.auth.presentation.Dto.DeletedUserInfoResponseDto;
import com.sparta_logistics.auth.presentation.Dto.SignUpForCompanyManagerRequestDto;
import com.sparta_logistics.auth.presentation.Dto.SignUpRequestDto;
import com.sparta_logistics.auth.presentation.Dto.UserChangePasswordReqDto;
import com.sparta_logistics.auth.presentation.Dto.UserInfoResponseDto;
import com.sparta_logistics.auth.presentation.Dto.UserUpdateRequestDto;
import com.sparta_logistics.auth.domain.model.Role;
import com.sparta_logistics.auth.domain.model.User;
import com.sparta_logistics.auth.domain.Repository.UserRepository;
import com.sparta_logistics.auth.application.Security.JwtUtil;
import com.sparta_logistics.auth.application.Security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final SecretKey secretKey;

  public AuthService(@Value("${service.jwt.secret-key}") String secretKey,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
  }

  /*
   * 회원 가입
   * */
  @Transactional
  public void signUp(SignUpRequestDto signUpRequestDto) {
    validateDuplicateUser(signUpRequestDto);

    Role role = validateAndGetRole(signUpRequestDto.getRole());
    User user = User.create(signUpRequestDto.getUserName(), signUpRequestDto.getPassword(),
        signUpRequestDto.getSlackId(), role, passwordEncoder);

    userRepository.save(user);
  }

  /*
   * 회원 가입 For Company Manager
   * */
  @Transactional
  public void signUpForCompanyManager(
      SignUpForCompanyManagerRequestDto signUpForCompanyManagerRequestDto) {

   SignUpRequestDto signUpRequestDto = SignUpRequestDto.from(signUpForCompanyManagerRequestDto);
    validateDuplicateUser(signUpRequestDto);
    Role role = validateAndGetRole(signUpRequestDto.getRole());
    User user = User.create(signUpRequestDto.getUserName(), signUpRequestDto.getPassword(),
        signUpRequestDto.getSlackId(), role, passwordEncoder);

    userRepository.save(user);
  }

  /*
   * 사용자 UserInfo 확인(auth/info)
   * */
  public Claims getUserInfoFromAccessToken(String accessToken) {
    // 2. 사용자 정보를 DB에서 조회
    return jwtUtil.getUserInfoFromToken(accessToken);
  }

  @Transactional
  public void changePassword(UserDetailsImpl userDetails, UserChangePasswordReqDto request) {
    User user = userRepository.findById(userDetails.getUser().getUserId())
        .orElseThrow(() -> new AuthException(ErrorCode.SAME_PASSWORD));
    request.validate(userDetails.getUser(), passwordEncoder);
    user.changePassword(passwordEncoder.encode(request.getNewPassword()));
  }

  /*
   *  사용자 SoftDelete 기능(auth/delete)
   * */
  @Transactional
  public void softDeleteUser(String accessToken) {
    String slackId = jwtUtil.getUserInfoFromToken(accessToken).get("slackId").toString();
    User user = userRepository.findActiveUserBySlackId(slackId)
        .orElseThrow(() -> new IllegalArgumentException("slackId가 존재하지 않음"));

    user.softDelete(user.getUserName());
  }

  // 회원가입시 회원 존재 여부 검증
  private void validateDuplicateUser(SignUpRequestDto signUpRequestDto) {
    if (userRepository.findBySlackId(signUpRequestDto.getSlackId()).isPresent()) {
      throw new AuthException(ErrorCode.DUPLICATED_SLACK_ID);
    }
    if (userRepository.findByUserName(signUpRequestDto.getUserName()).isPresent()) {
      throw new AuthException(ErrorCode.DUPLICATED_USERNAME);
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

  @Transactional
  public UserInfoResponseDto update(UserDetailsImpl userDetails, UserUpdateRequestDto request) {

    User user = userRepository.findById(userDetails.getUser().getUserId())
        .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다."));

    user.update(request);
    return new UserInfoResponseDto(user);
  }

  @Transactional
  public UserInfoResponseDto updateForMaster(UserDetailsImpl userDetails, UserUpdateRequestDto request,UUID userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("해당 User가 존재하지 않습니다."));

    user.update(request);
    user.setUpdatedBy(userDetails.getUser().getUserName());
    return new UserInfoResponseDto(user);
  }

  @Transactional(readOnly = true)
  public Page<UserInfoResponseDto> getAllUsers(String sortBy, int page, int size) {
    int realSize = ConfirmPageSize(size);
    Pageable pageable = PageRequest.of(page, realSize, Sort.by(sortBy).ascending());
    Page<User> userList = userRepository.findAllByIsDeletedFalse(pageable);
    return userList.map(UserInfoResponseDto::new);
  }

  @Transactional(readOnly = true)
  public Page<DeletedUserInfoResponseDto> getDeletedUsers(String sortBy, int page, int size) {
    int realSize = ConfirmPageSize(size);
    Pageable pageable = PageRequest.of(page, realSize, Sort.by(sortBy).ascending());
    Page<User> userList = userRepository.findAllByIsDeletedTrue(pageable); // isDeleted가 true인 사용자만 조회
    return userList.map(DeletedUserInfoResponseDto::new);
  }


  @Transactional(readOnly = true)
  public UserInfoResponseDto getOneUsersForMaster(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다."));
    return new UserInfoResponseDto(user);
  }


  @Transactional(readOnly = true)
  public UserInfoResponseDto getOneUsersForUser(UserDetailsImpl userDetails) {
    User user = userRepository.findById(userDetails.getUser().getUserId())
        .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다."));
    return new UserInfoResponseDto(user);
  }

  private int ConfirmPageSize(int size) {
    if ( size != 10 && size != 30 && size != 50){
      return 10;
    } else return size;
  }

}
