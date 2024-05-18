package com.sithub.sithub.Repository;

import com.sithub.sithub.domain.Gyumin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MariaRepository extends JpaRepository<Gyumin, Long> {
    Gyumin findByRoomId(String roomId);
}
