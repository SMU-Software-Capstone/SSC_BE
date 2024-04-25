package com.sithub.sithub.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.sithub.sithub.Repository.ManageRepository;
import com.sithub.sithub.Repository.ProjectRepository;
import com.sithub.sithub.Repository.TeamRepository;
import com.sithub.sithub.domain.Manage;
import com.sithub.sithub.domain.Project;
import com.sithub.sithub.domain.Team;
import com.sithub.sithub.responseDTO.ManageListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

    private final ProjectRepository projectRepository;

    public List<ManageListDTO> getManageList(String teamName, String projectName) {
        Project project = projectRepository.findByTeamNameAndName(teamName, projectName)
                .orElseThrow(() -> new NotFoundException("Could not found"));

        List<ManageListDTO> dtos = project.getManages().stream().map(
                manage -> new ManageListDTO(
                        manage.getId(), manage.getComment(), manage.getCreateDate())
        ).toList();

        for (ManageListDTO dto : dtos) {
            System.out.println("dto = " + dto);
        }
        return dtos;
    }
}
