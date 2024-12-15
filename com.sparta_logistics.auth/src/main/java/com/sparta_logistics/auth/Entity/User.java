package com.sparta_logistics.auth.Entity;

import com.sparta_logistics.auth.Dto.SignUpRequestDto;
import com.sparta_logistics.auth.Dto.UserUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_user")
@Builder
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "UUID", updatable = false, nullable = false)
  private UUID userId;

  @Column(nullable = false, length = 20)
  private String userName;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = 20)
  private String slackId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  private boolean isDeleted ;

  // 유저 생성 메서드
  public static User create(
      final String username,
      final String password,
      final String slackId,
      final Role role,
      PasswordEncoder passwordEncoder
  ) {
    User user = User.builder()
        .userName(username)
        .password(passwordEncoder.encode(password))
        .slackId(slackId)
        .role(role)
        .build();

    user.initAuditInfo(user);

    return user;

  }

  public void softDelete(String userName) {
    this.isDeleted = true;
    softDeleteUser(userName);
  }

  public void changePassword(String password) {
    this.password = password;
    this.initAuditInfo(this);
  }

  public void update(UserUpdateRequestDto requestDto) {

    if (requestDto.getUserName() != null && !requestDto.getUserName().isBlank()) {
      this.setUserName(requestDto.getUserName());
    }

    if (requestDto.getRole() != null) {
      this.setRole(requestDto.getRole());
    }

    if (requestDto.getSlackId() != null && !requestDto.getSlackId().isBlank()) {
      this.setSlackId(requestDto.getSlackId());
    }

    this.initAuditInfo(this);
    setUpdatedBy(requestDto.getUserName());
  }

}
