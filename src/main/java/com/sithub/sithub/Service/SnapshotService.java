package com.sithub.sithub.Service;

import com.sithub.sithub.Repository.SnapshotRepository;
import com.sithub.sithub.domain.Snapshot;
import com.sithub.sithub.responseDTO.SnapshotDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;

    public void saveSnapshot(String roomId, String fileName, List<String> code) {
        Snapshot snapshot = new Snapshot(roomId, fileName, code);
        snapshotRepository.save(snapshot);
    }

    public SnapshotDTO getSnapshotsByRoomId(String roomId) {
        Snapshot snapshots = snapshotRepository.findByRoomId(roomId);
        SnapshotDTO dto = convertToDTO(snapshots);
        return dto;
    }

    private SnapshotDTO convertToDTO(Snapshot snapshot) {
        SnapshotDTO dto = new SnapshotDTO();
        dto.setRoomId(snapshot.getRoomId());
        dto.setFileName(snapshot.getFileName());
        dto.setCode(snapshot.getCode());
        return dto;
    }


    public void updateCodes(String roomId, String code, int lineNumber) {
        Snapshot snapshot = snapshotRepository.findByRoomId(roomId);
        if(snapshot != null) {
            snapshot.updateCode(code, lineNumber);
            snapshotRepository.save(snapshot);
        }
    }
}
