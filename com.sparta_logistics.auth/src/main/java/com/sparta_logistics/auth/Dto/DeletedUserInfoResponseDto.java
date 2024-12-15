package com.sparta_logistics.auth.Dto;

import com.sparta_logistics.auth.Entity.Role;
import com.sparta_logistics.auth.Entity.User;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeletedUserInfoResponseDto
{
  private UUID userId;
  private String userName;
  private String slackId;
  private Role role;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String createdBy;
  private String updatedBy;

  private boolean isDeleted ;
  private LocalDateTime deletedAt;
  private String deletedBy;

  public DeletedUserInfoResponseDto(User user){
    this.userId = user.getUserId();
    this.userName = user.getUserName();
    this.slackId = user.getSlackId();
    this.role = user.getRole();
    this.createdAt = user.getCreatedAt();
    this.updatedAt = user.getUpdatedAt();
    this.createdBy = user.getCreatedBy();
    this.updatedBy = user.getUpdatedBy();
    this.isDeleted = user.isDeleted();
    this.deletedAt = user.getDeletedAt();
    this.deletedBy = user.getDeletedBy();
  }

}
