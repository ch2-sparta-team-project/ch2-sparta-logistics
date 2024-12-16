package com.sparta_logistics.ai.presentation.controller;

import com.sparta_logistics.ai.application.service.AIService;
import com.sparta_logistics.ai.presentation.dto.AiRequest;
import com.sparta_logistics.ai.presentation.dto.AiResponse;
import com.sparta_logistics.ai.presentation.dto.RequestUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiController {

  private final AIService aiService;

  @PostMapping
  @Secured({"MASTER", "HUB_TO_HUB_DELIVERY", "HUB_TO_COMPANY_DELIVERY"})
  public ResponseEntity<AiResponse> sendAI(
      @AuthenticationPrincipal RequestUserDetails user,
      @RequestBody AiRequest request
  ) {
    AiResponse response = aiService.sendAI(user.getRole(), request);
    return ResponseEntity.ok(response);
  }
}
