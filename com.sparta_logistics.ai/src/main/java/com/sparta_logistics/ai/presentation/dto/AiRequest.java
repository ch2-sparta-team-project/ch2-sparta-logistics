package com.sparta_logistics.ai.presentation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AiRequest {

  private UUID orderId;
  private String orderUsername;
  private String orderUserSlack;
  private String productName;
  private Integer productQuantity;
  private String orderRequest;
  private String deliverySource;
  private List<String> deliveryRoutes;
  private String deliveryDestination;
  private String deliveryManagerName;
  private String deliveryManagerSlackId;
}
