package com.sparta_logistics.slack;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeletedSlackInfoResponseDto {

  private UUID Id;
  private UUID userId;
  private String userName;
  private String slackId;
  private String message;
  private boolean isSend;
  private LocalDateTime sendTime;
  private boolean isDeleted;


  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String createdBy;
  private String updatedBy;
  private LocalDateTime deletedAt;
  private String deletedBy;

  public DeletedSlackInfoResponseDto(SlackEntity slack){
    this.Id = slack.getId();
    this.userId = slack.getUserId();
    this.userName = slack.getUserName();
    this.slackId = slack.getSlackId();
    this.message = slack.getMessage();
    this.isSend = slack.isSend();
    this.sendTime = slack.getSendTime();
    this.isDeleted = slack.isDeleted();

    this.createdAt = slack.getCreatedAt();
    this.updatedAt = slack.getUpdatedAt();
    this.createdBy = slack.getCreatedBy();
    this.updatedBy = slack.getUpdatedBy();
    this.deletedAt = slack.getDeletedAt();
    this.deletedBy = slack.getDeletedBy();
  }

}
