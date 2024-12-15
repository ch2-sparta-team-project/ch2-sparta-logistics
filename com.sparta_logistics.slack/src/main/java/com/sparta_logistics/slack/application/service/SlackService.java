package com.sparta_logistics.slack.application.service;


import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.users.UsersLookupByEmailResponse;
import com.sparta_logistics.slack.infrastructure.config.SlackChannel;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackService {

  @Value(value = "${slack.token}")
  String slackToken;

  private final Slack slack = Slack.getInstance();

  public void sendSlackMessage(String message, String channel) {

    String channelAddress = "";

    // 채널 값을 전달받아 올바른 슬랙채널로 분기
    if (channel.equals("delivery")) {
      channelAddress = SlackChannel.DELIVERY_CHANNEL;
    }

    try {
      MethodsClient methods = Slack.getInstance().methods(slackToken);

      ChatPostMessageRequest request = ChatPostMessageRequest.builder()
          .channel(channelAddress)
          .text(message)
          .build();

      methods.chatPostMessage(request);

      log.info("Slack " + channel + " 에 메시지 보냄");
    } catch (SlackApiException | IOException e) {
      log.error(e.getMessage());
    }
  }

  /*
   * userId(Email 형식) 기반으로 Slack ID 가져오기
   */
  public String getSlackIdByEmail(String email) throws IOException, SlackApiException {
    UsersLookupByEmailResponse response = slack.methods(slackToken)
        .usersLookupByEmail(req -> req.email(email));

    if (response.isOk() && response.getUser() != null) {
      return response.getUser().getId(); // Slack user ID
    } else {
      throw new RuntimeException("Failed to fetch Slack ID for email: " + email);
    }
  }

  /*
   * Slack ID를 통해 DM 보내기
   */
  public void sendDirectMessage(String slackId, String message)
      throws IOException, SlackApiException {
    ChatPostMessageResponse response = slack.methods(slackToken)
        .chatPostMessage(req -> req
            .channel(slackId) // Slack user ID
            .text(message));

    if (!response.isOk()) {
      throw new RuntimeException("Failed to send message: " + response.getError());
    }
  }

}