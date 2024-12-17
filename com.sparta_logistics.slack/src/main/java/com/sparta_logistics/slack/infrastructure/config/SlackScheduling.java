package com.sparta_logistics.slack.infrastructure.config;

import com.sparta_logistics.slack.application.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class SlackScheduling {

  @Autowired
  SlackService slackService;

  // 매일 아침 6시마다로 변경 예정 (도전 과제)
//  @Scheduled(cron="0 0/1 * * * *") //1분
//  public void batchTest() {
//    slackService.sendSlackMessage("물류 스케쥴링 테스트", "delivery");
//  }

}
