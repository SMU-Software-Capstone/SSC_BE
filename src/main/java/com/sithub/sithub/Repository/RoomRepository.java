package com.sithub.sithub.Repository;

import com.sithub.sithub.domain.Room;
import com.sithub.sithub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRandomId(@Param("randomId") String randomId);
}
