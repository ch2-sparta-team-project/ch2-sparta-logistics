package com.sparta_logistics.order.infrastructure.repository;

import com.sparta_logistics.order.domain.model.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

  Optional<Order> findByIdAndDeletedAtIsNull(String id);

  boolean existsByIdAndDeletedAtIsNull(String orderId);
}
