package com.sithub.sithub.controller;

import com.sithub.sithub.Service.SnapshotService;
import com.sithub.sithub.domain.Snapshot;
import com.sithub.sithub.requestDTO.CodeDTO;
import com.sithub.sithub.requestDTO.SnapshotRequestDTO;
import com.sithub.sithub.responseDTO.SnapshotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SnapshotController {
    @Autowired
    private SnapshotService snapshotService;

    @PostMapping("/snapshot")
    public void saveSnapshot(@RequestBody SnapshotRequestDTO request) {
        snapshotService.saveSnapshot(request.getRoomId(), request.getFileName(), request.getCode());
    }


    //방 들어갔을때 받아가는 데이터
    @GetMapping("/snapshots/{roomId}")
    public SnapshotDTO getSnapshotsByRoomId(@PathVariable("roomId") String roomId) {
        return snapshotService.getSnapshotsByRoomId(roomId);
    }

    //해당 방에서 코드를 수정했을때 저장
    @PatchMapping("/code/{roomId}")
    public void updateCode(@PathVariable("roomId") String roomId, @RequestBody CodeDTO code) {
        snapshotService.updateCodes(roomId, code.getCode(), code.getLineNumber());
    }
}