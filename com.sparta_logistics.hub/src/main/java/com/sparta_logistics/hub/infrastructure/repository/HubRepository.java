package com.sparta_logistics.hub.infrastructure.repository;

import com.sparta_logistics.hub.domain.model.Hub;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID>, HubRepositoryCustom {
  Hub findByIdAndDeletedAtIsNull(UUID hubId);
  Hub findByIdAndDeletedAtIsNotNull(UUID hubId);
  List<Hub> findByIdIn(List<UUID> idList);
  List<Hub> findByCenterHub(Hub hub);
  Hub findByNameAndDeletedAtIsNull(String hubName);
  List<Hub> findAllByDeletedAtIsNull();

}
