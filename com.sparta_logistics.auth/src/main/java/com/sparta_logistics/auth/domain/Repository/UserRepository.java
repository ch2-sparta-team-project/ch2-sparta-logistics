package com.sparta_logistics.auth.domain.Repository;

import com.sparta_logistics.auth.domain.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUserName(String username);

  Optional<User> findBySlackId(String slackId);

  Optional<User> findActiveUserBySlackId(String accessToken);

  Page<User> findAllByIsDeletedFalse(Pageable pageable); // isDeleted가 false인 사용자 조회

  Page<User> findAllByIsDeletedTrue(Pageable pageable);  // isDeleted가 true인 사용자 조회
}
