package com.sparta_logistics.order.domain.model;


import com.sparta_logistics.order.presentation.dto.RequestUserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import jakarta.persistence.PreRemove;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {

  @CreatedDate
  @Column(nullable = false, columnDefinition = "TIMESTAMP")
  private LocalDateTime createdAt;

  @CreatedBy
  @Column(columnDefinition = "varchar(255)")
  private String createdBy;

  @LastModifiedDate
  @Column(nullable = false, columnDefinition = "TIMESTAMP")
  private LocalDateTime updatedAt;

  @LastModifiedBy
  @Column(columnDefinition = "varchar(255)")
  private String updatedBy;

  private LocalDateTime deletedAt;
  private String deletedBy;

}
