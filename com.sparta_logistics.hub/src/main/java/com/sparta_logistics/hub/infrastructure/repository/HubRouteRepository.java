package com.sparta_logistics.hub.infrastructure.repository;

import com.sparta_logistics.hub.domain.model.HubRoute;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRouteRepository extends JpaRepository<HubRoute, UUID>{
  HubRoute findBySourceHubNameAndDestinationHubName(String sourceHubName, String destinationHubName);
  void deleteBySourceHubNameAndDestinationHubName(String sourceHubName, String destinationHubName);
}
