package com.sparta_logistics.hub.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
  @ManyToOne
  @JoinColumn(name = "center_hub_id")
  private Hub centerHub = null;
  @OneToMany(mappedBy = "centerHub")
  private Set<Hub> nearHubList = new HashSet<>();

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

  public void updateHub(UUID userId, String name, String address, Double longitude,
      Double latitude){
    this.userId = userId != null ? userId : this.userId;
    this.name = name != null ? name : this.name;
    this.address = address != null ? address : this.address;
    this.longitude = longitude != null ? longitude : this.longitude;
    this.latitude = latitude != null ? latitude : this.latitude;
  }

  public void deleteHub(String deletedBy) {
    setDeletedAt(LocalDateTime.now());
    setDeletedBy(deletedBy);
  }

  public void restoreHub() {
    setDeletedAt(null);
    setDeletedBy(null);
  }

  // 중심 허브 설정 활성화
  public void activateCenterHub(){
    this.isCenter = true;
  }

  // 중심 허브 설정 비활성화
  public void deactivateCenterHub(){
    this.isCenter = false;
    this.nearHubList.clear();
  }

  // 중심 허브 할당
  public void setCenterHub(Hub hub){
    this.centerHub = hub;
    hub.addNearHub(this);
  }

  // 인접 허브 추가
  public void addNearHub(Hub hub){
    this.nearHubList.add(hub);
    hub.setCenterHub(this);
  }

  // 인접 허브 삭제
  public void removeNearHub(Hub hub){
    this.nearHubList.remove(hub);
    hub.setCenterHub(null);
  }
}
