package com.sparta_logistics.ai.application.service;

import com.sparta_logistics.ai.infrastructure.client.AiFeignClient;
import com.sparta_logistics.ai.infrastructure.dto.GeminiRequest;
import com.sparta_logistics.ai.infrastructure.dto.GeminiResponse;
import com.sparta_logistics.ai.presentation.dto.AiRequest;
import com.sparta_logistics.ai.presentation.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {

  @Value("${gemini.api.key}")
  private String key;

  private final AiFeignClient aiFeignClient;

  public AiResponse sendAI(String role, AiRequest request) {
    String prompt = createMinimalPromptForDeadline(request);
    GeminiResponse response = aiFeignClient.sendGemini(key, new GeminiRequest(prompt));
    String answer = extractSingleAnswerFromResponse(response);
    log.info("Gemini Answer : {}", answer);
    return AiResponse.of(request, answer);
  }

  private String createMinimalPromptForDeadline(AiRequest aiRequest) {
    // 경유지 리스트를 문자열로 변환
    String deliveryRoutes =
        aiRequest.getDeliveryRoutes() != null && !aiRequest.getDeliveryRoutes().isEmpty()
            ? String.join(", ", aiRequest.getDeliveryRoutes())
            : "없음";

    // 간략화된 프롬프트 문자열 생성
    return String.format("""
            다음은 발송 시한을 계산하는 데 필요한 정보입니다. 이를 바탕으로 최종 발송 시한을 계산해주세요.

            상품 이름: %s
            상품 수량: %d
            주문 요청 사항(납기일자/시간): %s
            발송지: %s
            경유지: %s
            도착지: %s

            지도 상 발송지 경유지 도착지 거리를 바탕으로 대략적인 최종 발송 시한을 알려주세요.
            50자 이내로 알려주세요.
            """,
        aiRequest.getProductName(),
        aiRequest.getProductQuantity(),
        aiRequest.getOrderRequest(),
        aiRequest.getDeliverySource(),
        deliveryRoutes,
        aiRequest.getDeliveryDestination()
    );
  }

  private String extractSingleAnswerFromResponse(GeminiResponse response) {
    if (response == null || response.getCandidates() == null || response.getCandidates()
        .isEmpty()) {
      return "답변이 없습니다."; // 응답이 없거나 후보가 없는 경우 기본 메시지 반환
    }

    GeminiResponse.Candidate firstCandidate = response.getCandidates().get(0);
    if (firstCandidate.getContent() != null && firstCandidate.getContent().getParts() != null) {
      for (GeminiResponse.TextPart part : firstCandidate.getContent().getParts()) {
        if (part.getText() != null && !part.getText().isEmpty()) {
          return part.getText(); // 첫 번째 유효한 텍스트 반환
        }
      }
    }

    return "답변이 없습니다."; // 후보는 있으나 내용이 없는 경우 기본 메시지 반환
  }
}
