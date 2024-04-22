package com.sithub.sithub.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sithub.sithub.Repository.ManageRepository;
import com.sithub.sithub.Repository.SnapshotRepository;
import com.sithub.sithub.Repository.TeamRepository;
import com.sithub.sithub.config.StringToMultipartFileConverter;
import com.sithub.sithub.domain.File;
import com.sithub.sithub.domain.Manage;
import com.sithub.sithub.domain.Snapshot;
import com.sithub.sithub.domain.Team;
import com.sithub.sithub.responseDTO.SnapshotDTO;
import com.sithub.sithub.responseDTO.SnapshotListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;

    private final TeamRepository teamRepository;

    private final ManageRepository manageRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void saveSnapshot(String roomId, String fileName, List<String> code, String contentType) {
        Snapshot snapshot = new Snapshot(roomId, fileName, code, contentType);
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

    public void saveFile(List<MultipartFile> files, String teamName) throws IOException {
        List<Snapshot> prevSnapshots = snapshotRepository.findSnapshotsByRoomId(teamName);

        // 이전에 진행중이던 작업 내용은 삭제
        for (Snapshot prevSnapshot : prevSnapshots) {
            snapshotRepository.delete(prevSnapshot);
        }

        // 새로운 작업 내용 DB에 저장
        for (MultipartFile file : files) {
            if(file.getOriginalFilename().contains(".DS_Store")) {
                System.out.println("DS: " + file.getName());
                continue;
            }

            String code = new String(file.getBytes(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(new StringReader(code));

            String line;
            List<String> lineByCode = new ArrayList<>();

            while((line = bufferedReader.readLine()) != null) {
                lineByCode.add(line);
            }

            saveSnapshot(teamName, file.getOriginalFilename(), lineByCode, file.getContentType());
            bufferedReader.close();
        }
    }

    public void uploadToS3(String teamName, String comment) throws IOException {
        List<Snapshot> snapshots = snapshotRepository.findSnapshotsByRoomId(teamName);

        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + teamName));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String current_date = LocalDateTime.now().format(dateTimeFormatter);

        Manage manage = new Manage(comment);
        manage.setTeam(team);

        for (Snapshot snapshot : snapshots) {
            String code = "";

            for(String line : snapshot.getCode()) {
                code = code.concat(line).concat("\n");
            }

//            MultipartFile file = StringToMultipartFileConverter.convert(code, snapshot.getFileName(), snapshot.getContentType());
//
//            ObjectMetadata metadata = new ObjectMetadata();
//
//            metadata.setContentLength(file.getSize());
//            metadata.setContentType(file.getContentType());
//
//            amazonS3.putObject(bucket, snapshot.getFileName(), file.getInputStream(), metadata);
            amazonS3.putObject(bucket,teamName + current_date + "/" + snapshot.getFileName(), code);

            File file = new File(teamName + current_date + "/" + snapshot.getFileName());
            file.setManage(manage);

            System.out.println("upload: " + snapshot.getFileName());
        }

        manageRepository.save(manage);
    }

    public List<SnapshotListDTO> getSnapshotList(String teamName) {
        List<Snapshot> snapshots = snapshotRepository.findSnapshotsByRoomId(teamName);

        return snapshots.stream().map(
                snapshot -> new SnapshotListDTO(snapshot.getId(), snapshot.getFileName())).toList();
    }

    public String test() {
        String objectAsString = amazonS3.getObjectAsString(bucket, "folderTest/child/UserList.js");
        System.out.println(objectAsString);
        return objectAsString;
    }
}
