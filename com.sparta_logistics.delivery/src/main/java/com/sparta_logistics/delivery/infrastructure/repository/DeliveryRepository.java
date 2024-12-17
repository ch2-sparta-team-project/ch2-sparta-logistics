package com.sparta_logistics.delivery.infrastructure.repository;

import com.sparta_logistics.delivery.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
