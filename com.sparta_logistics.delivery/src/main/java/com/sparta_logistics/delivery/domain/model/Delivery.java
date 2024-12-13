package com.sparta_logistics.delivery.domain.model;

import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String sourceHubId;

  private String destinationHubId;

  private String orderId;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  private String address;

  private String recipientName;

  private String recipientSlackId;

}
