package com.sparta_logistics.ai.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiRequest {
  private String model;   // GPT 모델 이름 (예: gpt-3.5-turbo)
  private String prompt;  // 요청 프롬프트
  private int maxTokens;  // 최대 토큰 수
}

