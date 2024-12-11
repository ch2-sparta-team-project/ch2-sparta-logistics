package com.sparta_logistics.hub.domain.repository;

import com.sparta_logistics.hub.domain.model.Hub;
import com.sparta_logistics.hub.infrastructure.repository.HubRepositoryCustom;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID>, HubRepositoryCustom {

}
