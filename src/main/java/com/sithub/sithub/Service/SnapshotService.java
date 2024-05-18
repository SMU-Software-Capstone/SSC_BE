package com.sithub.sithub.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sithub.sithub.Repository.*;
import com.sithub.sithub.config.StringToMultipartFileConverter;
import com.sithub.sithub.domain.*;
import com.sithub.sithub.requestDTO.CreateSnapshotDTO;

import com.sithub.sithub.requestDTO.SnapshotRequestDTO;
import com.sithub.sithub.responseDTO.SnapshotDTO;
import com.sithub.sithub.responseDTO.SnapshotListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final FileRepository fileRepository;

    private final ManageRepository manageRepository;

    private final ProjectRepository projectRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void saveSnapshot(String roomId, String fileName, List<String> code, String contentType, String projectName) {
        Snapshot snapshot = new Snapshot(roomId, fileName, code, contentType, projectName);
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


    public void updateCodes(String teamName, String updateType, List<String> code, String fileName, String projectName, int lineNumber) {
        Snapshot snapshot = snapshotRepository.findByRoomIdAndFileNameAndProjectName(teamName, fileName, projectName)
                .orElseThrow(() -> new NotFoundException("Could not found id "));

        if(snapshot != null) {
            snapshot.updateCode(updateType, code, lineNumber);
            snapshotRepository.save(snapshot);
        }
    }

    public List<String> saveFile(List<MultipartFile> files, String teamName, String projectName) throws IOException {
        List<Snapshot> prevSnapshots = snapshotRepository.findSnapshotsByRoomIdAndProjectName(teamName, projectName);
        List<String> result = new ArrayList<>();

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

            saveSnapshot(teamName, file.getOriginalFilename(), lineByCode, file.getContentType(), projectName);
            result.add(file.getOriginalFilename());
            bufferedReader.close();
        }

        return result;
    }

    public void uploadToS3(String teamName, String projectName, String comment) throws IOException {
        List<Snapshot> snapshots = snapshotRepository.findSnapshotsByRoomId(teamName);

        Project project = projectRepository.findProjectByName(projectName)
                .orElseThrow(() -> new NotFoundException("Could not found"));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String current_date = LocalDateTime.now().format(dateTimeFormatter);

        Manage manage = new Manage(comment);
        manage.setProject(project);

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
            amazonS3.putObject(bucket,teamName + "-" + projectName + current_date + "/" + snapshot.getFileName(), code);

            File file = new File(teamName + "-" + projectName + current_date + "/" + snapshot.getFileName());
            file.setManage(manage);

            System.out.println("upload: " + snapshot.getFileName());
        }

        manageRepository.save(manage);
    }

    public List<String> getSnapshotList(String teamName, String projectName) {
        List<Snapshot> snapshots = snapshotRepository.findSnapshotsByRoomIdAndProjectName(teamName, projectName);

        return snapshots.stream().map(snapshot -> snapshot.getFileName()).toList();
    }

    public String getSnapshot(String teamName, String fileName, String projectName) {
        Snapshot snapshot = snapshotRepository.findByRoomIdAndFileNameAndProjectName(teamName, fileName, projectName)
                .orElseThrow(() -> new NotFoundException("Could not found id "));
        List<String> code = snapshot.getCode();
        String result = "";

        for (String s : code) {
            result += (s + "\n");
        }

        return result;
    }

    public List<String> changeSnapshot(String teamName, String projectName, Long manageId) throws IOException {
        List<File> files = fileRepository.findFilesByManageId(manageId);
        List<Snapshot> prevSnapshots = snapshotRepository.findSnapshotsByRoomIdAndProjectName(teamName, projectName);
        List<String> result = new ArrayList<>();

        // 이전에 진행중이던 작업 내용은 삭제
        for (Snapshot prevSnapshot : prevSnapshots) {
            snapshotRepository.delete(prevSnapshot);
        }

        for (File file : files) {
            String code = amazonS3.getObjectAsString(bucket, file.getName());
            BufferedReader bufferedReader = new BufferedReader(new StringReader(code));

            String line;
            List<String> lineByCode = new ArrayList<>();

            while((line = bufferedReader.readLine()) != null) {
                lineByCode.add(line);
            }

            saveSnapshot(teamName, file.getName(), lineByCode, "content", projectName);

            result.add(file.getName());
        }

        return result;
    }

    public String test() {
        String objectAsString = amazonS3.getObjectAsString(bucket, "folderTest/child/UserList.js");
        System.out.println(objectAsString);
        return objectAsString;
    }

    public void createNewSnapShot(CreateSnapshotDTO createSnapshotDTO){
        Snapshot snapshot = new Snapshot(createSnapshotDTO.getRoomId(), createSnapshotDTO.getFileName());
        snapshotRepository.save(snapshot);
    }

    public void removeSnapShot(CreateSnapshotDTO createSnapshotDTO) {
        // 나중에 수정
//        Snapshot snapshot = snapshotRepository.findByRoomIdAndFileName(createSnapshotDTO.getRoomId(), createSnapshotDTO.getFileName())
//                .orElseThrow(() -> new NotFoundException("Could not found"));
//        snapshotRepository.delete(snapshot);
    }

}
