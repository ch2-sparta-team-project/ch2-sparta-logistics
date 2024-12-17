package com.sparta_logistics.slack.presentation.Dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class SlackSendMessageRequestDto {

  private String message;
  private String channel;

  public SlackSendMessageRequestDto(String message, String channel) {
    this.message = message;
    this.channel = channel;
  }
}
