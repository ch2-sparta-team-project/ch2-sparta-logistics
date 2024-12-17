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
import java.util.ArrayList;
import java.util.List;
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

  @Column(nullable = false)
  private UUID userId;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String address;
  @Column(nullable = false)
  private Double longitude;
  @Column(nullable = false)
  private Double latitude;
  @Column(nullable = false)
  private Boolean isCenter;
  @ManyToOne
  @JoinColumn(name = "center_hub_id")
  private Hub centerHub = null;
  @OneToMany(mappedBy = "centerHub")
  private List<Hub> nearHubList;

  public static Hub createHub(UUID userId, String name, String address, Double longitude,
      Double latitude, Boolean isCenter) {
    return Hub.builder()
        .userId(userId)
        .name(name)
        .address(address)
        .longitude(longitude)
        .latitude(latitude)
        .isCenter(isCenter)
        .nearHubList(new ArrayList<>())
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
    this.centerHub = this;
  }

  // 중심 허브 설정 비활성화
  public void deactivateCenterHub(){
    this.isCenter = false;
    this.nearHubList.clear();
    this.centerHub = null;
  }

  // 중심 허브 할당
  public void setCenterHub(Hub hub){
    this.centerHub = hub;
    if (hub != null){
      hub.addNearHub(this);
    }
  }

  // 인접 허브 추가
  public void addNearHub(Hub hub){
    if (!this.nearHubList.contains(hub)){
      this.nearHubList.add(hub);
      hub.setCenterHub(this);
    }
  }

  // 인접 허브 삭제
  public void removeNearHub(Hub hub){
    this.nearHubList.remove(hub);
    hub.setCenterHub(null);
  }
}
