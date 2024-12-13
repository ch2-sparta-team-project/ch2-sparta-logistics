package com.sparta_logistics.delivery.domain.model;

import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
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
public class DeliveryManager {

  @Id
  @GeneratedValue
  private String id;

  @Column(nullable = false)
  private String hubId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DeliveryManagerRole role;

  @Column(nullable = false)
  private Integer turn;

}
