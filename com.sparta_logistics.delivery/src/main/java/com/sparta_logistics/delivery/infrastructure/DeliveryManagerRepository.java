package com.sparta_logistics.delivery.infrastructure;

import com.sparta_logistics.delivery.domain.model.DeliveryManager;
import com.sparta_logistics.delivery.domain.model.enumerate.DeliveryManagerRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryManagerRepository extends JpaRepository<DeliveryManager, String> {
  @Query("SELECT MAX(d.turn) FROM DeliveryManager d WHERE d.role = :role")
  Optional<Integer> findMaxTurnByRole(@Param("role") DeliveryManagerRole role);

  @Query("SELECT MAX(d.turn) FROM DeliveryManager  d WHERE d.role = :role AND d.hubId = :hubId")
  Optional<Integer> findMaxTurnByRoleAndHubId(@Param("role") DeliveryManagerRole role, @Param("hubId") String hubId);

  Optional<DeliveryManager> findByIdAndDeletedAtIsNull(String deliveryManagerId);

  List<DeliveryManager> findAllByDeletedAtIsNull();

  List<DeliveryManager> findAllByIdAndDeletedAtIsNull(String id);
}
