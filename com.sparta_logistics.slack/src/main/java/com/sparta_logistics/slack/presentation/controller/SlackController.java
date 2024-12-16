package com.sparta_logistics.slack.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.methods.SlackApiException;
import com.sparta_logistics.slack.application.service.SlackService;
import com.sparta_logistics.slack.infrastructure.client.AuthServiceClient;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RefreshScope
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/slack")
public class SlackController {

  private final SlackService slackService;
  private final AuthServiceClient authServiceClient;

  @GetMapping("/DELIVERY_CHANNEL")
  public void DELIVERY_CHANNEL_ALL() {

    log.info("슬랙 채널 테스트");

    slackService.sendSlackMessage("슬랙 배달 채널 테스트", "delivery");
  }

  // feignCleint 설정 DM Message 테스트,
  // 추후 authServiceClient.sendInfo부분 변경하여 배달 정보로 교체
  @GetMapping("/DM")
  public ResponseEntity<?> Dm(@RequestHeader("Authorization") String token)
      throws SlackApiException, IOException {

    log.info(authServiceClient.sendInfo(token).toString());
    ResponseEntity<?> response = authServiceClient.sendInfo(token);

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> responseBody = objectMapper.convertValue(response.getBody(), Map.class);
    String slackId = (String) responseBody.get("slackId");

    log.info("받아온 slackId 는 " + slackId + " 입니다.");
    log.info(slackService.getSlackIdByEmail(slackId));

    //추후 AiServiceClient에서 배달 메세지를 받아와 전송 하도록 변경
    slackService.sendDirectMessage(slackService.getSlackIdByEmail(slackId),
        authServiceClient.sendInfo(token).toString());
    return ResponseEntity.ok(authServiceClient.sendInfo(token).getBody());
  }

}
