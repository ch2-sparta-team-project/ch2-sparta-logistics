package com.sparta_logistics.auth.Repository;

import com.sparta_logistics.auth.Entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUsername(String username);

  Optional<User> findBySlackId(String slackId);

}
