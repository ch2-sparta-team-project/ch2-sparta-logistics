package com.sparta_logistics.ai.infrastructure.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenAiResponse {
  private List<Choice> choices;

  @Getter
  @Setter
  @NoArgsConstructor
  public static class Choice {
    private Message message;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Message {
      private String content; // 응답 텍스트
    }
  }
}

