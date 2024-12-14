package com.sparta_logistics.delivery.domain.model;

import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jdk.jfr.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE p_delivery SET deleted_at = NOW() where id = ?")
@Table(name = "p_delivery")
public class Delivery extends Base{

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String sourceHubId;

  @Column(nullable = false)
  private String destinationHubId;

  @Column(nullable = false)
  private String orderId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DeliveryStatus status;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String recipientName;

  @Column(nullable = false)
  private String recipientSlackId;

  @Column(nullable = false)
  private LocalDateTime estimatedArrivalTime;

  @Column(nullable = true)
  private LocalDateTime arrivalTime;
}
