package com.sparta_logistics.slack.application.Repository;

import com.sparta_logistics.slack.SlackEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackRepository extends JpaRepository<SlackEntity, UUID> {

  Page<SlackEntity> findAllByIsSendTrue(Pageable pageable);

  Page<SlackEntity> findAllByIsSendFalse(Pageable pageable);

}
