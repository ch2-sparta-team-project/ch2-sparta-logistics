package com.sparta_logistics.slack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.sparta_logistics.slack")
public class SlackApplication {

  public static void main(String[] args) {
    SpringApplication.run(SlackApplication.class, args);
  }

}
