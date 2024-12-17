package com.sparta_logistics.order.infrastructure.client.dto.user;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoResponseDto {

  private UUID userId;
  private String userName;
  private String slackId;

}