package com.sparta_logistics.slack.application.Repository;

import com.sparta_logistics.slack.SlackEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackRepository extends JpaRepository<SlackEntity, UUID> {

  Optional<SlackEntity> findByUserName(String username);

  Optional<SlackEntity> findBySlackId(String slackId);

}
