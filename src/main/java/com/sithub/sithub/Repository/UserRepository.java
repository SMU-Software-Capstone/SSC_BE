package com.sithub.sithub.Repository;

import com.sithub.sithub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserStringId(@Param("userId") String userId);
}
