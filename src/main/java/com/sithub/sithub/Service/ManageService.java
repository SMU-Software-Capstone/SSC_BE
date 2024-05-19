package com.sithub.sithub.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.sithub.sithub.Repository.*;
import com.sithub.sithub.domain.*;
import com.sithub.sithub.responseDTO.ManageListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

    private final FileRepository fileRepository;

    private final ManageRepository manageRepository;

    private final SnapshotRepository snapshotRepository;

    private final ProjectRepository projectRepository;

    public List<ManageListDTO> getManageList(String teamName, String projectName) {
        Project project = projectRepository.findByTeamNameAndName(teamName, projectName)
                .orElseThrow(() -> new NotFoundException("Could not found"));

        List<ManageListDTO> dtos = project.getManages().stream().map(
                manage -> new ManageListDTO(
                        manage.getId(), manage.getComment(), manage.getCreateDate())
                )
                .sorted(Comparator.comparing(ManageListDTO::getCreateDate).reversed()) // CreateDate 기준 내림차순 정렬
                .collect(Collectors.toList());

        for (ManageListDTO dto : dtos) {
            System.out.println("dto = " + dto);
        }
        return dtos;
    }

    // 해당 커밋 파일목록 조회
    public List<String> getFileList(Long manageId) {
        List<File> files = fileRepository.findFilesByManageId(manageId);

        List<String> result = new ArrayList<>();

        for (File file : files) {
            result.add(file.getName());
        }

        return result;
    }

    // 해당 커밋을 불러와서 스냅샷에 저장 후 에디터에 반영

}
