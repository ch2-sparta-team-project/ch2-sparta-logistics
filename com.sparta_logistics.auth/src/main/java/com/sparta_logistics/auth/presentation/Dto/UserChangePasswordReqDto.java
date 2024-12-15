package com.sparta_logistics.auth.presentation.Dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta_logistics.auth.domain.model.User;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserChangePasswordReqDto {

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$])[A-Za-z\\d@$!%*#?&]{8,15}$",

      message = "비밀번호는 8~15자의 알파벳 대소문자, 숫자, 특수문자를 포함해야 합니다.")
  private String newPassword;

  public void validate(User user, PasswordEncoder passwordEncoder) {
    validateSamePassword(user, passwordEncoder);
  }

  private void validateSamePassword(User user, PasswordEncoder passwordEncoder) {
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      throw new RuntimeException("동일한 비밀번호로 변경할 수 없습니다.");
    }
  }


}
