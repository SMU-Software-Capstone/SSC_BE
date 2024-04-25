package com.sithub.sithub.controller;

import com.sithub.sithub.Service.ManageService;
import com.sithub.sithub.responseDTO.ManageListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {
    private final ManageService manageService;

    @GetMapping("/list/{teamName}/{projectName}")
    public List<ManageListDTO> list(@PathVariable("teamName") String teamName,
                                    @PathVariable("projectName") String projectName) {
        return manageService.getManageList(teamName, projectName);
    }
}
