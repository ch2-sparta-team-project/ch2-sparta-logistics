package com.sparta_logistics.ai.presentation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AiResponse {

  private String answer;

  public static AiResponse of(AiRequest aiRequest, String answer) {
    // 경유지를 문자열로 변환
    String deliveryRoutes = String.join(", ", aiRequest.getDeliveryRoutes());

    // 결과 메시지 생성
    String message = String.format("""
            주문 번호 : %s
            주문자 정보 : %s / %s
            상품 정보 : %s %d박스
            요청 사항 : %s
            발송지 : %s
            경유지 : %s
            도착지 : %s
            배송담당자 : %s / %s

            AI 답변 : %s
            """,
        aiRequest.getOrderId(),
        aiRequest.getOrderUsername(), aiRequest.getOrderUserSlack(),
        aiRequest.getProductName(), aiRequest.getProductQuantity(),
        aiRequest.getOrderRequest(),
        aiRequest.getDeliverySource(),
        deliveryRoutes.isEmpty() ? "없음" : deliveryRoutes,
        aiRequest.getDeliveryDestination(),
        aiRequest.getDeliveryManagerName(), aiRequest.getDeliveryManagerSlackId(),
        answer
    );
    return new AiResponse(message);
  }
}
