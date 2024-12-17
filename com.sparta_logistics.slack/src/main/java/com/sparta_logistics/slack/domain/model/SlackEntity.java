package com.sparta_logistics.slack.domain.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_slack")
@Builder
public class SlackEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "UUID", updatable = false, nullable = false)
  private UUID Id;

  @Column(columnDefinition = "UUID", updatable = false, nullable = false)
  private UUID userId;

  @Column(nullable = false, length = 20)
  private String userName;

  @Column(nullable = false, length = 100)
  private String slackId;

  @Column(nullable = false)
  private String message;

  private boolean isSend;

  private LocalDateTime sendTime;

  private boolean isDeleted ;

  // 메세지 생성 메서드
  public static SlackEntity create(
      final UUID userId,
      final String username,
      final String slackId,
      final String message,
      final boolean isSend,
      final LocalDateTime sendTime
  ) {
    SlackEntity slackEntity = SlackEntity.builder()
        .userId(userId)
        .userName(username)
        .slackId(slackId)
        .message(message)
        .isSend(isSend)
        .sendTime(sendTime)
        .build();

    slackEntity.initAuditInfo(slackEntity);

    return slackEntity;

  }

  public void softDelete(SlackEntity slack) {
    this.isDeleted = true;
    softDeleteUser(slack);
  }

}
