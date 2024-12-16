package com.sparta_logistics.slack.domain.repository;

import com.sparta_logistics.slack.domain.model.SlackEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackRepository extends JpaRepository<SlackEntity, UUID> {

  Page<SlackEntity> findAllByIsDeletedTrue(Pageable pageable);

  Page<SlackEntity> findAllByIsDeletedFalse(Pageable pageable);

}
