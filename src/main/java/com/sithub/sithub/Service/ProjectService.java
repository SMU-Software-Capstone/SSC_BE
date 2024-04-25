package com.sithub.sithub.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.sithub.sithub.Repository.ProjectRepository;
import com.sithub.sithub.Repository.TeamRepository;
import com.sithub.sithub.domain.Project;
import com.sithub.sithub.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void createProject(String projectName, String teamName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + teamName));

        Project project = new Project(projectName);
        project.setTeam(team);

        projectRepository.save(project);
    }

    public List<String> projectList(String teamName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + teamName));

        List<Project> projects = projectRepository.findProjectsByTeamId(team.getId());

        return projects.stream().map(project -> project.getName()).toList();
    }
}
