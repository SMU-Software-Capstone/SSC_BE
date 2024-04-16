package com.sithub.sithub.controller;

import com.sithub.sithub.Service.TeamService;
import com.sithub.sithub.config.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final Util util;

    @Value("${jwt.secret}")
    private String secretKey;

    private final TeamService teamService;

    @PostMapping("/create/{teamName}")
    public String create(@CookieValue String token,
                       @PathVariable("teamName") String teamName) {
        Long id = util.getUserId(token, secretKey);
        teamService.createTeam(id, teamName);
        return "success";
    }

    @PostMapping("/{teamName}/add/{nickname}")
    public String add(@PathVariable("teamName") String teamName,
                      @PathVariable("nickname") String nickname) {
        return teamService.addUser(teamName, nickname);
    }
}
