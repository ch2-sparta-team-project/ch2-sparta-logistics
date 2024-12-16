package com.sparta_logistics.slack.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.methods.SlackApiException;
import com.sparta_logistics.slack.SlackSendMessageRequestDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

//  @GetMapping("/DELIVERY_CHANNEL")
//  public ResponseEntity<?> DELIVERY_CHANNEL_ALL() {
//
//    log.info("슬랙 채널 테스트");
//
//    slackService.sendSlackMessage("슬랙 배달 채널 테스트", "delivery");
//    return ResponseEntity.ok("notice send Test");
//  }

  @PostMapping("/notice")
  public ResponseEntity<?> NoticeSend(@RequestHeader("Authorization") String token,
      @RequestBody SlackSendMessageRequestDto requestDto)
      throws SlackApiException, IOException {
    log.info("공지 보내기");
    slackService.sendSlackMessage(requestDto, token);
    return ResponseEntity.ok("notice send success");
  }


  // 추후 authServiceClient.sendInfo부분 변경하여 배달 정보로 교체
  @GetMapping("/DM")
  public ResponseEntity<?> Dm(@RequestHeader("Authorization") String token)
      throws SlackApiException, IOException {

    ResponseEntity<?> response = authServiceClient.findInfo(token);

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> responseBody = objectMapper.convertValue(response.getBody(), Map.class);
    String slackId = (String) responseBody.get("slackId");

    /**
     * 추후 AiServiceClient에서 배달 메세지를 받아와 전송 하도록 변경
     * */
    slackService.sendDirectMessage(slackService.getSlackIdByEmail(slackId),
        authServiceClient.findInfo(token).toString());
    return ResponseEntity.ok(authServiceClient.findInfo(token).getBody());
  }

}
