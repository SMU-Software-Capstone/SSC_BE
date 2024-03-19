package com.sithub.sithub.controller;

import com.sithub.sithub.Service.EditorService;
import com.sithub.sithub.config.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorService editorService;
    private final Util util;
    @GetMapping("/connect")
    public String editor() {
        return UUID.randomUUID().toString();
    }

    @GetMapping("/create")
    public String createRoom(String title){
        editorService.create(title);
        return "Success";
    }
}
