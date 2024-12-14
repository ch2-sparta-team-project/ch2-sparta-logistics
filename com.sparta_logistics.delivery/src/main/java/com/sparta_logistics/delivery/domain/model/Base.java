package com.sparta_logistics.delivery.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
