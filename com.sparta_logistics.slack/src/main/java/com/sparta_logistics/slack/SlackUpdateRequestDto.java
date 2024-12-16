package com.sparta_logistics.slack;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlackUpdateRequestDto {

  private UUID userId;
  private String userName;
  private String slackId;
  private String message;
  private boolean isSend;
  private LocalDateTime sendTime;

}
