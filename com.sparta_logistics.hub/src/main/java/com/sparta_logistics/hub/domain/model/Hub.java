package com.sparta_logistics.hub.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_hub")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Hub extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "p_hub_id")
  private UUID id;

  private UUID userId;
  private String name;
  private String address;
  private Double longitude;
  private Double latitude;
  private Boolean isCenter;

  public static Hub createHub(UUID userId, String name, String address, Double longitude,
      Double latitude, Boolean isCenter) {
    return Hub.builder()
        .userId(userId)
        .name(name)
        .address(address)
        .longitude(longitude)
        .latitude(latitude)
        .isCenter(isCenter)
        .build();
  }

  public void deleteHub(String deletedBy) {
    setDeletedAt(LocalDateTime.now());
    setDeletedBy(deletedBy);
  }
}
