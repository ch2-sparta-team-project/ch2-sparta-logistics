package com.sparta_logistics.delivery.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@SQLDelete(sql = "UPDATE p_delivery_route SET deleted_at = NOW() where id = ?")
@Table(name = "p_delivery_route")
public class DeliveryRoute {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String deliveryManagerId;

  @Column(nullable = false)
  private String sourceHubId;

  @Column(nullable = false)
  private String destinationHubId;

  @Column(nullable = false)
  private String deliveryId;

  @Column(nullable = false)
  private Integer sequence;

  @Column(nullable = false)
  private Double estimatedDistance;

  @Column(nullable = false)
  private Long estimatedDuration;

  @Column()
  private Double distance;

  @Column()
  private Long duration;




}
