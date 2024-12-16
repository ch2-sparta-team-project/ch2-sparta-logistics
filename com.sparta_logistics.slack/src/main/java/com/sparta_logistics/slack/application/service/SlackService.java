package com.sparta_logistics.slack.application.service;


import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.users.UsersLookupByEmailResponse;
import com.sparta_logistics.slack.SlackEntity;
import com.sparta_logistics.slack.SlackSendMessageRequestDto;
import com.sparta_logistics.slack.UserInfoResponseDto;
import com.sparta_logistics.slack.application.Repository.SlackRepository;
import com.sparta_logistics.slack.infrastructure.client.AuthServiceClient;
import com.sparta_logistics.slack.infrastructure.config.SlackChannel;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlackService {

  @Value(value = "${slack.token}")
  String slackToken;

  private final Slack slack = Slack.getInstance();
  private final SlackRepository slackRepository;
  private final AuthServiceClient authServiceClient;

  //슬랙 "채널" 에 메세지 발송(전체 공지 기능)
  public void sendSlackMessage(SlackSendMessageRequestDto requestDto, String token) {

    String channelAddress = "";

    // 채널 값을 전달받아 올바른 슬랙채널로 분기
    if (requestDto.getChannel().equals("delivery")) {
      channelAddress = SlackChannel.DELIVERY_CHANNEL;
    } else if (requestDto.getChannel().equals("notice")) {
      channelAddress = SlackChannel.NOTICE_CHANNEL;
    }
    try {
      MethodsClient methods = Slack.getInstance().methods(slackToken);

      ChatPostMessageRequest request = ChatPostMessageRequest.builder()
          .channel(channelAddress)
          .text(requestDto.getMessage())
          .build();

      ResponseEntity<UserInfoResponseDto> info = authServiceClient.findInfo(token);
      methods.chatPostMessage(request);

      SlackEntity slack = SlackEntity.create(
          info.getBody().getUserId(), info.getBody().getUserName(), info.getBody().getSlackId(),
          requestDto.getMessage(),
          true, LocalDateTime.now());

      slackRepository.save(slack);

      log.info("Slack " + requestDto.getChannel() + " 에 메시지 보냄");
      log.info("message 내용은 : " + requestDto.getMessage());
    } catch (SlackApiException | IOException e) {
      log.error(e.getMessage());
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
      throw new RuntimeException("메세지 발신에 실패했습니다.: " + response.getError());
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
      throw new RuntimeException("SlackID(Email)를 통해 내부ID를 불러오는 데 실패했습니다. " + email);
    }
  }


}