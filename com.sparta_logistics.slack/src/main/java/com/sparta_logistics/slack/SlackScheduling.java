package com.sparta_logistics.slack;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class SlackScheduling {

  @Autowired
  SlackService slackService;

  // 매일 아침 6시마다로 변경 예정
  @Scheduled(cron="0 0/1 * * * *") //1분
  public void batchTest(){
    slackService.sendSlackMessage("물류 스케쥴링 테스트", "delivery");
  }

}
