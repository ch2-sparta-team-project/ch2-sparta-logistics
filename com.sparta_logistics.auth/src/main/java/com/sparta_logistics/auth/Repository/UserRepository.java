package com.sparta_logistics.auth.Repository;

import com.sparta_logistics.auth.Entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUserName(String username);

  Optional<User> findBySlackId(String slackId);

  Optional<User> findActiveUserBySlackId(String accessToken);
}
