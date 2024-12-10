package com.sparta_logistics.auth.Entity;

import com.sparta_logistics.auth.Dto.SignUpDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "msa_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "UUID", updatable = false, nullable = false)
  private UUID id = UUID.randomUUID();

  private String username;
  private String password;
  private String slackId;
  private Role role;
  private Status status;

  public User(SignUpDto signUpDto, String password) {
    this.username = signUpDto.getUsername();
    this.password = password;
    this.slackId = signUpDto.getSlackId();
    this.role = signUpDto.getRole();
  }
}
