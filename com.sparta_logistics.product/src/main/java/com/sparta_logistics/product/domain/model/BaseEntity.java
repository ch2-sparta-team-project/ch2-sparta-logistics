package com.sparta_logistics.product.domain.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  @CreatedDate
  private LocalDateTime createdAt;

  @CreatedBy
  private String createdBy;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @LastModifiedBy
  private String updatedBy;

  private LocalDateTime deletedAt;

  private String deletedBy;
}
