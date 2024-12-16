package com.sparta_logistics.delivery.domain.model;

import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerStatus;
import jakarta.persistence.Column;
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
@SQLDelete(sql = "UPDATE p_delivery_manager SET deleted_at = NOW() where id = ?")
@Table(name = "p_delivery_manager")
public class DeliveryManager extends Base {

  @Id // user id와 같아야 하므로 자동 생성 사용 X
  private String id;

  @Column(nullable = false)
  private String hubId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DeliveryManagerRole role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DeliveryManagerStatus status;

  @Column(nullable = false)
  private Integer turn;

  public static DeliveryManager create(String id, String hubId, DeliveryManagerRole role,
      DeliveryManagerStatus status, Integer turn) {
    return DeliveryManager.builder()
        .id(id)
        .hubId(hubId)
        .role(role)
        .status(status)
        .turn(turn)
        .build();
  }

  public void updateStatus(DeliveryManagerStatus status) {
    if (status != null) {
      this.status = status;
    }
  }

  public void updateHub(String hubId) {
    if (hubId != null) {
      this.hubId = hubId;
    }
  }

  public void updateTurn(int turn) {
    this.turn = turn;
  }
}
