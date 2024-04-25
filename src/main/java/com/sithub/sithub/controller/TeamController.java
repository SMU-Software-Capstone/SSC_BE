package com.sithub.sithub.controller;

import com.sithub.sithub.Service.ProjectService;
import com.sithub.sithub.Service.TeamService;
import com.sithub.sithub.config.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final Util util;

    @Value("${jwt.secret}")
    private String secretKey;

    private final TeamService teamService;

    private final ProjectService projectService;

    // 팀 생성
    @PostMapping("/create/{teamName}")
    public String create(@CookieValue("token") String token,
                       @PathVariable("teamName") String teamName) {
        Long id = util.getUserId(token, secretKey);
        teamService.createTeam(id, teamName);
        return "success";
    }

    // 팀에 사용자 추가
    @PostMapping("/{teamName}/add/{nickname}")
    public String add(@PathVariable("teamName") String teamName,
                      @PathVariable("nickname") String nickname) {
        return teamService.addUser(teamName, nickname);
    }

    // 팀 소속 사용자 조회
    @GetMapping("/{teamName}/users")
    public List<String> users(@PathVariable("teamName") String teamName) {
        return teamService.userList(teamName);
    }

    // 프로젝트 생성
    @PostMapping("/{teamName}/create/{projectName}")
    public void createProject(@PathVariable("projectName") String projectName,
                       @PathVariable("teamName") String teamName) {
        projectService.createProject(projectName, teamName);
    }

    //프로젝트 조회
    @GetMapping("/{teamName}/projects")
    public List<String> projects(@PathVariable("teamName") String teamName) {
        return projectService.projectList(teamName);
    }
}
