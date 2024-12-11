package com.sparta_logistics.auth.Entity;

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
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "UUID", updatable = false, nullable = false)
  private UUID id = UUID.randomUUID();

  @Column(nullable = false, length = 20)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = 20)
  private String slackId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  // 유저 생성 메서드
  public static User create(
      final String username,
      final String password,
      final String slackId,
      final Role role,
      PasswordEncoder passwordEncoder
  ) {
    return User.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .slackId(slackId)
        .role(role)
        .build();
  }
}
