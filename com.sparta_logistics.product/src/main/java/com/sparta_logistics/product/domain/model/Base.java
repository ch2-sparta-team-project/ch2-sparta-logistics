package com.sparta_logistics.product.domain.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {

  @CreatedDate
  private LocalDateTime createdAt;

  private String createdBy;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  private String updatedBy;

  private LocalDateTime deletedAt;

  private String deletedBy;

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

}
