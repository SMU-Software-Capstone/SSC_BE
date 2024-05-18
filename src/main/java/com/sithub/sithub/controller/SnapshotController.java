package com.sithub.sithub.controller;

import com.sithub.sithub.Service.MariaService;
import com.sithub.sithub.Service.SnapshotService;
import com.sithub.sithub.domain.Snapshot;
import com.sithub.sithub.requestDTO.*;
import com.sithub.sithub.responseDTO.SnapshotDTO;
import com.sithub.sithub.responseDTO.SnapshotListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SnapshotController {

    private final SnapshotService snapshotService;
    private final MariaService mariaService;

//    @PostMapping("/snapshot")
//    public void saveSnapshot(@RequestBody SnapshotRequestDTO request/*MariaDTO mariaDTO*/) {
//        snapshotService.saveSnapshot(request.getRoomId(), request.getFileName(), request.getCode());
//        //mariaService.saveSnapshot(mariaDTO.getRoomId(), mariaDTO.getCode());
//    }

    @GetMapping("/healthy")
    public String healthy() {
        return "success";
    }

    //방 들어갔을때 받아가는 데이터
    @GetMapping("/snapshots/{roomId}")
    public SnapshotDTO getSnapshotsByRoomId(@PathVariable("roomId") String roomId) {
        return snapshotService.getSnapshotsByRoomId(roomId);
    }

    //해당 방에서 코드를 수정했을때 저장
//    @PatchMapping("/code/{roomId}")
//    public void updateCode(@PathVariable("roomId") String roomId, @RequestBody CodeDTO code) {
//        snapshotService.updateCodes(roomId, code.getUpdateType(), code.getCode(), code.get code.getLineNumber());
//        //mariaService.updateCodes(roomId, code.getCode());
//    }

    //새 프로젝트 파일 받기
    @PostMapping("/{teamName}/{projectName}/snapshots/save")
    public List<String> save(
            @RequestPart(value = "files") List<MultipartFile> files,
            @PathVariable("teamName") String teamName,
            @PathVariable("projectName") String projectName) throws IOException {
        return snapshotService.saveFile(files, teamName, projectName);
    }

    //작업중인 프로젝트 S3에 업로드
    @PostMapping("/s3/upload")
    public void upload(@RequestBody S3UploadDTO dto) throws IOException {
        snapshotService.uploadToS3(dto.getTeamName(), dto.getProjectName(), dto.getComment());
    }

    // 작업중인 프로젝트 파일 목록
    @GetMapping("/snapshot/list/{teamName}/{projectName}")
    public List<String> snapshotList(@PathVariable("teamName") String teamName,
                                     @PathVariable("projectName") String projectName) {
        return snapshotService.getSnapshotList(teamName, projectName);
    }

    // 선택한 파일 코드 반환
    @GetMapping("/snapshot/{teamName}/{projectName}")
    public String snapshot(@PathVariable("teamName") String teamName,
                                 @PathVariable("projectName") String projectName,
                                 @RequestParam("fileName") String fileName) {
        return snapshotService.getSnapshot(teamName, fileName, projectName);
    }

    // 커밋로그에서 선택하여 에디터 시작
    @PostMapping("/snapshot/change")
    public List<String> changeSnapshot(@RequestBody ChangeSnapshotDTO dto) throws IOException {
        return snapshotService.changeSnapshot(
                dto.getTeamName(),
                dto.getProjectName(),
                dto.getCommitId());
    }

    @GetMapping("/test/file")
    public String test() {
        return snapshotService.test();
    }

    @PostMapping("/snapshot/create")
    public void createSnapShot(@RequestBody CreateSnapshotDTO createSnapshotDTO){
        snapshotService.createNewSnapShot(createSnapshotDTO);
    }

    @PostMapping("/snapshot/remove")
    public void removeSnapShot(@RequestBody CreateSnapshotDTO createSnapshotDTO){
        snapshotService.removeSnapShot(createSnapshotDTO);
    }
}