package com.sparta_logistics.delivery.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@SQLDelete(sql = "UPDATE p_delivery_hub_to_company_route SET deleted_at = NOW() where id = ?")
@Table(name = "p_delivery_hub_to_company_route")
public class DeliveryHubToCompanyRoute extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_manager_id", nullable = true) //배정할 매니저가 없을 시 null
  private DeliveryManager companyDeliveryManagerId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id", nullable = false)
  private Delivery delivery;

  @Column(nullable = false)
  private String sourceHubId;

  @Column(nullable = false)
  private String destinationAddress;

  @Column(nullable = false)
  private Integer sequence;

  @Column(nullable = false)
  private Double estimatedDistance;

  @Column(nullable = false)
  private Long estimatedDuration;

  @Column(nullable = true)
  private Double distance;

  @Column(nullable = true)
  private Long duration;

  public static DeliveryHubToCompanyRoute create(DeliveryManager companyDeliveryManagerId,
      Delivery delivery, String sourceHubId, String destinationAddress,
      Integer sequence, Double estimatedDistance, Long estimatedDuration
  ) {
    return DeliveryHubToCompanyRoute.builder()
        .companyDeliveryManagerId(companyDeliveryManagerId)
        .delivery(delivery)
        .sourceHubId(sourceHubId)
        .destinationAddress(destinationAddress)
        .sequence(sequence)
        .estimatedDistance(estimatedDistance)
        .estimatedDuration(estimatedDuration)
        .build();
  }
}