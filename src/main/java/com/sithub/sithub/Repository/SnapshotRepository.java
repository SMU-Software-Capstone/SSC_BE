package com.sithub.sithub.Repository;

import com.sithub.sithub.domain.Snapshot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SnapshotRepository extends MongoRepository<Snapshot, String> {
    Snapshot findByRoomId(String roomId);

    List<Snapshot> findSnapshotsByRoomId(String roomId);

    Snapshot findByRoomIdAndFileName(String roomId, String fileName);
}
