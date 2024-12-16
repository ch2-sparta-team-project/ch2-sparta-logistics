package com.sparta_logistics.hub.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_hub_route")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubRoute {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "hub_route_id")
  private UUID id;

  @ManyToOne
  private Hub sourceHub;

  @ManyToOne
  private Hub destinationHub;

  private String sourceHubName;

  private String destinationHubName;

  private LocalTime duration;

  private Double distance;

  public static HubRoute createHubRoute(Hub sourceHub, Hub destinationHub, LocalTime duration, Double distance){
    return HubRoute.builder()
        .sourceHub(sourceHub)
        .destinationHub(destinationHub)
        .sourceHubName(sourceHub.getName())
        .destinationHubName(destinationHub.getName())
        .duration(duration)
        .distance(distance)
        .build();
  }

  public void update(LocalTime duration, Double distance){
    this.duration = duration != null ? duration : this.duration;
    this.distance = distance != null ? distance : this.distance;
  }
}
