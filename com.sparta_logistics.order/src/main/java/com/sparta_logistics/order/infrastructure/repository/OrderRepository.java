package com.sparta_logistics.order.infrastructure.repository;

import com.sparta_logistics.order.domain.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

  Optional<Order> findByIdAndDeletedAtIsNull(String id);

  List<Order> findAllByDeletedAtIsNull();

  List<Order> findAllByUserIdAndDeletedAtIsNull(String userId);
}
