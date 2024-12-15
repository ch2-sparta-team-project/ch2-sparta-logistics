package com.sparta_logistics.slack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
  public void DELIVERY_CHANNEL_ALL(){

    log.info("슬랙 채널 테스트");

    slackService.sendSlackMessage("슬랙 배달 채널 테스트", "delivery");
  }

  //feignCleint 설정
  @GetMapping("/TEST")
  public void Test(@RequestHeader("Authorization") String token){

    log.info(authServiceClient.sendInfo(token));

    slackService.sendSlackMessage(authServiceClient.sendInfo(token), "delivery");
  }

}
